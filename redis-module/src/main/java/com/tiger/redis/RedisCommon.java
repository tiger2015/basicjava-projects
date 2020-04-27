package com.tiger.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCommon {

    private static JedisPoolConfig poolConfig;
    static {
        poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(4);
        poolConfig.setMaxIdle(2);
        poolConfig.setMaxWaitMillis(3000);
        poolConfig.setTestOnBorrow(true);
    }

    public static JedisPool createJedisPool() {
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.100.201", 6379, 3000, "tiger");
        return jedisPool;
    }

}
