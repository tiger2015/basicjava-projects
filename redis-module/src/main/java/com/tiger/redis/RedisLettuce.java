package com.tiger.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.ScoredValueScanCursor;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.resource.ClientResources;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RedisLettuce {
    public static void main(String[] args) {
        // standalone();
        sentinel();
    }

    private static void standalone() {
        RedisClient client = RedisClient.create("redis://tiger@192.168.100.201:6379/0");
        StatefulRedisConnection<String, String> connect = client.connect();
        log.info("connect");
        RedisCommands<String, String> commands = connect.sync();
        ScoredValueScanCursor<String> zset = commands.zscan("zset");
        List<ScoredValue<String>> values = zset.getValues();
        values.forEach(System.out::println);
        connect.close();
        client.shutdown();
        log.info("shutdown");
    }

    private static void sentinel() {
        RedisURI.Builder builder = RedisURI.builder();
        builder.withSentinel("192.168.100.201", 26379)
                .withSentinel("192.168.100.201", 26380)
                .withSentinel("192.168.100.201", 26381)
                .withSentinelMasterId("mymaster")
                .withDatabase(0)
                .withPassword("tiger");
        RedisURI uri = builder.build();
        // redis-sentinel://tiger@192.168.100.201,192.168.100.201:26380,192.168.100.201:26381?sentinelMasterId=mymaster
        log.info("uri:{}", uri.toURI());
        RedisClient client = RedisClient.create(uri);
        StatefulRedisConnection<String, String> connect = client.connect();
        RedisCommands<String, String> sync = connect.sync();
        List<String> list = sync.lrange("list", 0, -1);
        list.forEach(e -> log.info("{}", e));
        connect.close();
        client.shutdown();
    }

    private static void cluster(){
        List<RedisURI> uris = new ArrayList<>();
        uris.add(RedisURI.create("redis://tiger@192.168.100.201:8379/0"));
        uris.add(RedisURI.create("redis://tiger@192.168.100.201:8380/0"));
        uris.add(RedisURI.create("redis://tiger@192.168.100.201:8381/0"));
        RedisClusterClient client = RedisClusterClient.create(uris);




    }

}
