package example

import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.functions._

object Conversion {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession.builder().appName("Conversion").master("local[*]").getOrCreate()
        val sc = spark.sparkContext
        val dsdf: DataFrame = spark.read.json("C:\\data\\user_conversion.json")
        dsdf.show()

        val spec: WindowSpec = Window.partitionBy(col("userid")).orderBy(col("actionTime"))
        val conversionDF: DataFrame = dsdf.withColumn("index", row_number().over(spec))
            .withColumn("pageNext", lead(col("pageid"), 1).over(spec))
            .withColumn("jump", concat_ws("->", col("pageid"), col("pageNext")))

        val pageDF: DataFrame = conversionDF.groupBy(col("pageid")).agg(count("*").alias("pageSumCount"))
        val jumpDF: DataFrame = conversionDF.groupBy(col("jump")).agg(count("*").alias("jumpSumCount"))
        val result: Dataset[Row] = pageDF.join(conversionDF, Seq("pageid"), "inner").join(jumpDF, Seq("jump"), "inner")
            .withColumn("conversion", col("jumpSumCount").cast("Double") / col("pageSumCount"))
            .filter(col("jump").contains("->")).select(col("jump"), col("conversion"))
            .distinct().orderBy(col("jump"))

        val util = new HBaseUtil
        util.init()
        util.createTable("conversion")
        util.close()

        result.rdd.foreachPartition(item => {
            val util = new HBaseUtil
            util.init()
            item.foreach(row => {
                val jump: String = row.getAs[String]("jump")
                val conv: Double = row.getAs[Double]("conversion")
                val bean = new conBean(jump, conv)
                util.convBeanPut("conversion", bean)
            })
            util.close()
        })
    }
}
