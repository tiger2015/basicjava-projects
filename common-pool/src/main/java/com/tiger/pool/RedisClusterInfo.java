package com.tiger.pool;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.SlotHash;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/28 21:46
 * @Description:
 * @Version: 1.0
 **/
@Slf4j
public class RedisClusterInfo {

    private static final int UPDATE_INTERVAL = 3000;
    private ScheduledExecutorService scheduleThreadPool = Executors.newSingleThreadScheduledExecutor();

    private RedisClusterConfig clusterConfig;

    private Map<String, RedisNode> redisNodes; // redis集群的节点信息

    private List<RedisClient> clients; // 负责更新节点信息

    private GenericKeyedObjectPoolConfig<RedisConnection> poolConfig = new GenericKeyedObjectPoolConfig<>();

    private GenericKeyedObjectPool<String, RedisConnection> clusterConnectionPool = new GenericKeyedObjectPool<>(new RedisConnectionKeyPooledObjectFactory(this), poolConfig); // 连接池

    public RedisClusterInfo(RedisClusterConfig clusterConfig, RedisConnectionPoolConfig poolConfig) {
        this.clusterConfig = clusterConfig;
        this.poolConfig.setMaxTotalPerKey(poolConfig.getMaxTotal());
        this.poolConfig.setMaxIdlePerKey(poolConfig.getMaxIdle());
        this.poolConfig.setMinIdlePerKey(poolConfig.getMinIdle());
        this.poolConfig.setMaxWaitMillis(poolConfig.getMaxWaitMills());
        this.redisNodes = new ConcurrentHashMap<>();
        this.clients = new ArrayList<>();
    }

    /**
     * 初始化集群连接信息，该配置主要用于更新redis集群所有节点信息
     */
    public void init() {
        List<RedisClusterConfig.Node> nodes = clusterConfig.getNodes();
        for (RedisClusterConfig.Node node : nodes) {
            RedisURI.Builder builder = RedisURI.builder();
            builder.withTimeout(Duration.ofMillis(clusterConfig.getMaxWaitMills()));
            builder.withDatabase(clusterConfig.getDb());
            builder.withPassword(clusterConfig.getPassword());
            builder.withHost(node.host);
            builder.withPort(node.port);
            clients.add(RedisClient.create(builder.build()));
        }
        updateClusterNodes();
        scheduleThreadPool.scheduleAtFixedRate(() -> {
            updateClusterNodes();
        }, UPDATE_INTERVAL, UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     * 更新集群节点状态，需要定时更新
     */
    private void updateClusterNodes() {
        Map<String, RedisNode> redisNodeMap = new HashMap<>();
        for (RedisClient client : clients) {
            StatefulRedisConnection<String, String> connect = null;
            try {
                connect = client.connect();
                RedisCommands<String, String> sync = connect.sync();
                String nodes = sync.clusterNodes();
                String[] split = nodes.split("\n");
                for (String node : split) {
                    RedisNode redisNode = RedisNode.parser(node);
                    redisNodeMap.put(redisNode.getId(), redisNode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connect != null)
                    connect.close();
            }
        }
        log.info("update cluster node");
        redisNodes.putAll(redisNodeMap);
    }

    // 获取节点
    public RedisClient getRedisClient(String key) {
        if (!redisNodes.containsKey(key)) {
            throw new NoSuchElementException("could't find node:" + key);
        }
        RedisNode redisNode = redisNodes.get(key);
        RedisURI.Builder builder = RedisURI.builder();
        builder.withTimeout(Duration.ofMillis(clusterConfig.getMaxWaitMills()));
        builder.withDatabase(clusterConfig.getDb());
        builder.withPassword(clusterConfig.getPassword());
        builder.withHost(redisNode.getIp());
        builder.withPort(redisNode.getPort());
        return RedisClient.create(builder.build());
    }

    public RedisConnection borrowObject(String redisKey) throws SlotNotAvailableException, Exception, NoRedisNodeServiceForSlotException {
        int slot = SlotHash.getSlot(redisKey);
        for (String key : redisNodes.keySet()) {
            RedisNode redisNode = redisNodes.get(key);
            if (!redisNode.containsSlot(slot)) continue;
            if (redisNode.getMode() != RedisNode.Mode.MASTER) continue;
            if (redisNode.getState() == RedisNode.State.CONNECTED)
                return clusterConnectionPool.borrowObject(key);
            else {
                throw new SlotNotAvailableException(redisNode, slot);
            }
        }
        throw new NoRedisNodeServiceForSlotException(slot);
    }


    public void returnObject(RedisConnection redisConnection) {
        clusterConnectionPool.returnObject(redisConnection.getKey(), redisConnection);
    }

    public void destroy() {
        scheduleThreadPool.shutdown();
        clusterConnectionPool.close();
    }
}
