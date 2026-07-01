package example

import org.apache.spark.SparkContext
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object AmazonOrder {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession.builder().appName("Amazon").master("local[*]").getOrCreate()
        val sc: SparkContext = spark.sparkContext
        val dsdf: DataFrame = spark.read.json("C:\\data\\user_session.json")

        val spec: WindowSpec = Window.partitionBy("address_name").orderBy(col("product_count").desc)

        val resultTemp: Dataset[Row] = dsdf.groupBy("address_name", "product_id").agg(count("*").alias("product_count"))
            .orderBy("address_name").withColumn("index", row_number().over(spec))
            .filter(col("index") <= 3)

        val df5115: DataFrame = resultTemp.filter(col("product_id") === "1005115").select("address_name", "product_count").toDF("address_name", "prod5115")
        val df4856: DataFrame = resultTemp.filter(col("product_id") === "1004856").select("address_name", "product_count").toDF("address_name", "prod4856")
        val df4767: DataFrame = resultTemp.filter(col("product_id") === "1004767").select("address_name", "product_count").toDF("address_name", "prod4767")

        val result: DataFrame = df5115.join(df4856, Seq("address_name"), "inner").join(df4767, Seq("address_name"), "inner")

        val adminUtil = new HBaseUtil
        adminUtil.init()
        adminUtil.createTable("top3")
        adminUtil.close()

        result.rdd.foreachPartition { item =>
            val util = new HBaseUtil
            util.init()
            item.foreach { row =>
                val add: String = row.getAs[String]("address_name")
                val pro5115: Long = row.getAs[Long]("prod5115")
                val pro4856: Long = row.getAs[Long]("prod4856")
                val pro4767: Long = row.getAs[Long]("prod4767")
                val prod = new Product(add, pro5115, pro4856, pro4767)
                util.productPut("top3", prod)
            }
            util.close()
        }
    }
}
