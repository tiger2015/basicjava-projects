package com.tiger.redis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.200.201", 6379, 3000, "test");
        return jedisPool;
    }

    public static JedisSentinelPool createJedisSentinelPool() {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.100.201:26379");
        sentinels.add("192.168.100.201:26380");
        sentinels.add("192.168.100.201:26381");
        JedisSentinelPool jedisPool = new JedisSentinelPool("mymaster", sentinels, poolConfig, "tiger");
        return jedisPool;
    }


    public static JedisCluster createJedisCluster() {
        Set<HostAndPort> nodes = new LinkedHashSet<>();
        nodes.add(new HostAndPort("192.168.200.201", 8379));
        nodes.add(new HostAndPort("192.168.200.201", 8380));
        nodes.add(new HostAndPort("192.168.200.201", 8381));
        JedisCluster cluster = new JedisCluster(nodes, 5000, 3000, 5, "tiger", poolConfig);
        return cluster;
    }
}
