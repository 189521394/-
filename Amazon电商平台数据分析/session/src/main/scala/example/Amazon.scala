package example

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object Amazon {
    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession.builder().appName("Amazon").master("local[*]").getOrCreate()
        val sc: SparkContext = spark.sparkContext
        val dsdf: DataFrame = spark.read.json("C:\\data\\user_session.json")
        dsdf.show()
        dsdf.groupBy("category_id", "event_type").count().orderBy("category_id").show()
        val viewDF: DataFrame = dsdf.filter(col("event_type") === "view").groupBy("category_id").agg(count("*").alias("viewCount"))
        val cartDF: DataFrame = dsdf.filter(col("event_type") === "cart").groupBy("category_id").agg(count("*").alias("cartCount"))
        val purchaseDF: DataFrame = dsdf.filter(col("event_type") === "purchase").groupBy("category_id").agg(count("*").alias("purchaseCount"))
        val resultDF: DataFrame = viewDF.join(cartDF, Seq("category_id"), "inner").join(purchaseDF, Seq("category_id"), "inner")

        val hb = new HBaseUtil()
        hb.init()
        hb.createTable("categoryCount")

        val top10: Array[Info] = resultDF.orderBy(col("viewCount").desc, col("cartCount").desc, col("purchaseCount").desc).take(10)
            .map(row => Info(row.getAs[String]("category_id"), row.getAs[Long]("viewCount"), row.getAs[Long]("cartCount"), row.getAs[Long]("purchaseCount")))
        hb.infoPut("categoryCount", top10.toIndexedSeq)
        hb.close()
    }
}
