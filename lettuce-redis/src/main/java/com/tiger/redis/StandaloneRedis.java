package com.tiger.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.ScoredValueScanCursor;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.rebloom.client.Client;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.SearchResult;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class StandaloneRedis {
    public static void main(String[] args) {
       // lettuceTest();
        redisSearchTest();

    }

    private static void lettuceTest() {
        RedisClient client = RedisClient.create("redis://test@192.168.200.201:6379/0");
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

    private static void bloomTest() {
        Client bloomClient = new Client(createJedisPool());
        bloomClient.createFilter("test", 10000, 0.0001);
    }


    private static void redisSearchTest() {
        io.redisearch.client.Client client = new io.redisearch.client.Client("testung", createJedisPool());
        Schema schema = new Schema()
                .addTextField("title",5.0)
                .addTextField("body", 1.0)
                .addNumericField("price");
        //client.createIndex(schema, io.redisearch.client.Client.IndexOptions.defaultOptions());

        Map<String, Object> fields = new HashMap<>();
        fields.put("title", "hello world");
        fields.put("body", "lorem ipsum");
        fields.put("price", 1337);
       // client.addDocument("doc1", fields);

        Query query = new Query("hello world")
                .addFilter(new Query.NumericFilter("price",0, 10000))
                .limit(0,5);
        SearchResult result = client.search(query);
        result.docs.forEach(e->log.info(e.toString()));
    }

    private static JedisPool createJedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(4);
        poolConfig.setMaxIdle(2);
        poolConfig.setMaxWaitMillis(3000);
        poolConfig.setTestOnBorrow(true);
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.200.201", 6379, 3, "test");
        return jedisPool;
    }

}
