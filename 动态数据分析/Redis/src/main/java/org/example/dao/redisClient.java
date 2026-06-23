package org.example.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;

public class redisClient {
    private static Jedis jedis;
    private static JedisPool jedisPool;
    
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
    
    public static void closeJedis(Jedis jedis) {
        jedis.close();
    }
    
    private redisClient() {
    
    }
    
    static {
        Properties prop = new Properties();
        try {
            prop.load(redisClient.class.getClassLoader().getResourceAsStream("redis.properties"));
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(Integer.valueOf(prop.getProperty("jedis.max.total")));
            poolConfig.setMaxIdle(Integer.valueOf(prop.getProperty("jedis.max.idle")));
            poolConfig.setMinIdle(Integer.valueOf(prop.getProperty("jedis.min.idle")));
            poolConfig.setMaxWaitMillis(Long.valueOf(prop.getProperty("jedis.max.wait.millis")));
            String host = prop.getProperty("jedis.host");
            String port = prop.getProperty("jedis.port");
            jedisPool = new JedisPool(poolConfig, host, Integer.valueOf(port), 10000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
