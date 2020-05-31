package com.tiger.rmi.serviceimpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
    private static JedisPool jedisPool;
    private static final String host = "192.168.100.201";
    private static final int port = 6379;
    private static final String password = "tiger";

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(3000);
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(4);
        poolConfig.setMinIdle(2);
        jedisPool = new JedisPool(poolConfig, host, port, 3000, password);
    }

    public static Jedis connect() {
        return jedisPool.getResource();
    }

    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
