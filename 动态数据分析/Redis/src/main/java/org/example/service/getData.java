package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.redisClient;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class getData {
    public String getInfo() {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = redisClient.getJedis();
            Map<String, String> orders = jedis.hgetAll("orderTotal");
            Object[] objects = new Object[2];
            String[] products = new String[orders.size()];
            String[] prices = new String[orders.size()];
            Integer index = 0;
            for (Map.Entry<String, String> order : orders.entrySet()) {
                products[index] = order.getKey();
                prices[index] = order.getValue();
                index++;
            }
            objects[0] = products;
            objects[1] = prices;
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.writeValueAsString(objects);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } finally {
            if (jedis != null) {
                redisClient.closeJedis(jedis);
            }
        }
        return result;
    }
}
