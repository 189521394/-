package r

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool

import java.util.Properties

object redisUtil {
    val prop = new Properties()
    prop.load(this.getClass.getClassLoader.getResourceAsStream("redis.properties"))
    val redisHost: String = prop.getProperty("jedis.host")
    val redisPort: Int = prop.getProperty("jedis.port").toInt
    val timeOut: Int = prop.getProperty("jedis.timeout", "30000").toInt
    val pool = new JedisPool(new GenericObjectPoolConfig(), redisHost, redisPort, timeOut)
}
