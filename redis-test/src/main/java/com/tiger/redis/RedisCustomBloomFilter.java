package com.tiger.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.util.JedisClusterCRC16;

/**
 * @ClassName RedisCustomBloomFilter
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/4/23 10:20
 * @Version 1.0
 **/
@Slf4j
public class RedisCustomBloomFilter {
    public static void main(String[] args) {
        JedisCluster cluster = RedisCommon.createJedisCluster();
        int slot = JedisClusterCRC16.getSlot("test");
        Jedis jedis = cluster.getConnectionFromSlot(slot);
        jedis.set("test", "test");
        jedis.close();
    }

}
