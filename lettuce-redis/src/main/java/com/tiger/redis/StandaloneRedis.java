package com.tiger.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.ScoredValueScanCursor;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

public class StandaloneRedis {
    public static void main(String[] args) {

        RedisClient client = RedisClient.create("redis://tiger@192.168.100.201:6379/0");
        StatefulRedisConnection<String, String> connect = client.connect();
        System.out.println("connect");
        RedisCommands<String, String> commands = connect.sync();
        ScoredValueScanCursor<String> zset = commands.zscan("zset");
        List<ScoredValue<String>> values = zset.getValues();
        values.forEach(System.out::println);
        connect.close();
        client.shutdown();
        System.out.println("shutdown");


    }
}
