package r

import com.alibaba.fastjson.JSON
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import redis.clients.jedis.Jedis

import java.lang

object processData {
    def main(args: Array[String]): Unit = {
        val conf: SparkConf = new SparkConf()
            .setAppName("processData")
            .setMaster("local[*]")
        val sc = new SparkContext(conf)
        val ssc = new StreamingContext(sc, Seconds(5))
        val kafkaParams: Map[String, Object] = Map[String, Object](
            "bootstrap.servers" -> "hadoop1:9092,hadoop2:9092,hadoop3:9092",
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "spark_product",
            "auto.offset.reset" -> "latest",
            "enable.auto.commit" -> (false: lang.Boolean)
        )
        val topics = Array("product_order")
        val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(ssc, LocationStrategies.PreferConsistent, ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))
        stream.map(cr => cr.value())
            .map(order => JSON.parseObject(order))
            .map(obj => (obj.getString("name"), obj.getDouble("price")))
            .groupByKey().map(t => (t._1, t._2.reduceLeft(_ + _)))
            .foreachRDD(rdd => {
                rdd.foreachPartition(orders => {
                    orders.foreach(t2 => {
                        val jedis: Jedis = redisUtil.pool.getResource
                        jedis.hincrByFloat("orderTotal", t2._1, t2._2)
                        jedis.close()
                    })
                })
            })

        ssc.start()
        ssc.awaitTermination()
    }
}
