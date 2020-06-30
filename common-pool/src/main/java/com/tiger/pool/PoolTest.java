package com.tiger.pool;

import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/26 17:57
 * @Description:
 * @Version: 1.0
 **/
@Slf4j
public class PoolTest {

    public static void main(String[] args) throws InterruptedException {
        RedisConnectionPoolConfig poolConfig = new RedisConnectionPoolConfig();
        RedisClusterConfig clusterConfig = new RedisClusterConfig();
        clusterConfig.setPassword("tiger");
        List<RedisClusterConfig.Node> nodes = new ArrayList<>();
        nodes.add(new RedisClusterConfig.Node("192.168.100.201", 8379));
        nodes.add(new RedisClusterConfig.Node("192.168.100.201", 8380));
        clusterConfig.setNodes(nodes);
        RedisClusterInfo clusterInfo = new RedisClusterInfo(clusterConfig, poolConfig);
        clusterInfo.init();
        AtomicInteger index = new AtomicInteger(1);
        for (int i = 0; i < 40; i++) {
            TimeUnit.MILLISECONDS.sleep(5000);
            new Thread(() -> {
                RedisConnection redisConnection = null;
                try {
                    int temp = index.getAndIncrement();
                    redisConnection = clusterInfo.borrowObject("list" + temp);
                    StatefulRedisConnection<String, String> connection = redisConnection.getRedisConnection();
                    connection.sync().lpush("list"+temp, "item0", "item1");
                    index.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (SlotNotAvailableException e) {
                    e.printStackTrace();
                } catch (NoRedisNodeServiceForSlotException e) {
                    e.printStackTrace();
                } finally {
                    clusterInfo.returnObject(redisConnection);
                }
            }).start();
        }
    }


}
