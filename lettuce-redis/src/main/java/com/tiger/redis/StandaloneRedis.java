package com.tiger.redis;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StandaloneRedis {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

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

        //==================sentinel========================
        //== redis-sentinel://tiger@192.168.100.201,192.168.100.201:26380,192.168.100.201:26381?sentinelMasterId=mymaster
        //===========================================
        RedisURI sentinelUri = RedisURI.builder()
                .withSentinel("192.168.100.201", 26379)
                .withSentinel("192.168.100.201", 26380)
                .withSentinel("192.168.100.201", 26381)
                .withPassword("tiger")
                .withSentinelMasterId("mymaster").build();
        System.out.println(sentinelUri.toURI());

        RedisClient sentinelClient = RedisClient.create(sentinelUri);
        StatefulRedisConnection<String, String> sentinelConnect = sentinelClient.connect();
        RedisAsyncCommands<String, String> sentinelCommands = sentinelConnect.async();
        RedisFuture<List<String>> list = sentinelCommands.lrange("list", 0, -1);
        List<String> strings = list.get();
        strings.forEach(System.out::println);
        connect.close();
        sentinelClient.shutdown();
    }

}
