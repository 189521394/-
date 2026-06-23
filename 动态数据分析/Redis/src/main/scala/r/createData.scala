package r

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.json4s.DefaultFormats
import org.json4s.jackson.Json

import java.util.{Properties, UUID}
import scala.util.Random

case class Product(ID: String, name: String, price: Double)



object createData {
    def main(args: Array[String]): Unit = {
        val prop = new Properties()
        prop.put("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092")
        prop.put("acks", "all")
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        var products = Array("新鲜果蔬类","肉禽水产类","粮油干货类","休闲零食类","乳制品类","日化洗护类","速冻食品类","酒水饮料类","厨房调味类","针织家居类")
        val producer = new KafkaProducer[String, String](prop)
        while (true) {
            val id: String = UUID.randomUUID().toString.replaceAll("-", "")
            val index: Int = new Random().nextInt(products.length)
            val name: String = products(index)
            val price: Int = new Random().nextInt(500)
            val product = Product(id, name, price)
            val json = new Json(DefaultFormats)
            val proStr: String = json.write(product)
            producer.send(new ProducerRecord[String, String]("product_order", proStr))
            Thread.sleep(1000)
        }
    }
}
