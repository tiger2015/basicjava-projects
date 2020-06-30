package com.tiger.pool;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

import java.util.Objects;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/29 21:42
 * @Description:
 * @Version: 1.0
 **/
public class RedisConnection {
    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> redisConnection;
    private String key;

    public RedisConnection(String key, RedisClient redisClient) {
        this.key = key;
        this.redisClient = redisClient;
    }

    public void connect() {
        redisConnection = redisClient.connect();
    }

    public void close() {
        if (!Objects.isNull(redisConnection))
            redisConnection.close();
        if (!Objects.isNull(redisClient))
            redisClient.shutdown();
    }

    public boolean isConnected() {
        if (!Objects.isNull(redisConnection))
            return redisConnection.isOpen();
        return false;
    }

    public String getKey() {
        return key;
    }

    public StatefulRedisConnection<String, String> getRedisConnection() {
        return redisConnection;
    }

}
