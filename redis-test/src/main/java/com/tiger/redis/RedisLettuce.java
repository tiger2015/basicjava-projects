package com.tiger.redis;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisLettuce {
    public static void main(String[] args) {
        standalone();
       // sentinel();

        //redisFuture();
        // futureWithListener();
    }

    private static void standalone() {
        RedisURI.Builder builder = RedisURI.builder();
        builder.withHost("192.168.100.201");
        builder.withPort(6379);
        builder.withPassword("tiger");
        builder.withDatabase(0);


       // RedisClient client = RedisClient.create("redis://tiger@192.168.100.201:6379/0");
        RedisClient client = RedisClient.create(builder.build());

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
        StatefulRedisClusterConnection<String, String> connect = client.connect();
    }

    private static void pipeline(){
        RedisClient client = RedisClient.create("redis://test@192.168.200.201:6379/0");
        client.setDefaultTimeout(Duration.ofSeconds(3));
        StatefulRedisConnection<String, String> connect = client.connect();
        RedisAsyncCommands<String, String> async = connect.async();
        // pipeline禁用自动提交
        async.setAutoFlushCommands(false);
        long start;
        log.info("start:{}", start = System.currentTimeMillis());
        List<RedisFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            RedisFuture<String> future = async.set("key_" + i, i + "");
            futures.add(future);
            // future.get(); 会阻塞当前线程
        }
        async.flushCommands();
        // 等待返回结果
        LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futures.toArray(new RedisFuture[0]));
        long end;
        log.info("end:{}", end = System.currentTimeMillis());
        log.info("time:{}", end - start);
        connect.close();
        client.shutdown();
    }

    private static void redisFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();
        log.info("current state:{}", future.isDone());
        future.complete("value");
        log.info("current state:{}", future.isDone());
        try {
            log.info("future value:{}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static  void futureWithListener(){
        final CompletableFuture<String> future = new CompletableFuture<>();
        future.thenRun(() -> {
            try {
                log.info("future value:{}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        future.complete("set");
        log.info("future state:{}", future.isDone());

        Flux.just("Ben", "Michael", "Mark").subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



}
