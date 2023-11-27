package com.tiger.datastructure.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Author Zenghu
 * @Date 2021/12/12
 * @Description
 * @Version: 1.0
 **/
public class ConsistentHash<T> {
    private final TreeMap<Long, T> nodeHashMap = new TreeMap<>();
    public int virtualReplicas = 160; // 虚拟节点数
    private int hasCode;

    public ConsistentHash(int virtualReplicas, Set<T> nodes) {
        this.virtualReplicas = virtualReplicas;
        this.hasCode = nodes.hashCode();
        for (T node : nodes) {
            addNode(node);
        }
    }

    public ConsistentHash(Set<T> nodes) {
        this.hasCode = nodes.hashCode();
        for (T node : nodes) {
            addNode(node);
        }
    }

    /**
     * 当节点发生变化时，需要重新构建映射
     *
     * @param nodes
     * @param key
     * @param <K>
     * @return
     */
    public <K> T doSelect(Set<T> nodes, K key) {
        int hashCode = nodes.hashCode();
        if (hashCode == this.hasCode) {
            return select(key);
        }
        synchronized (this) {
            if (hashCode != this.hasCode) {
                nodeHashMap.clear();
                for (T node : nodes) {
                    addNode(node);
                }
                this.hasCode = hashCode;
            }
        }
        return select(key);
    }


    private <K> T select(K key) {
        byte[] bytes = md5(key.toString());
        long hash = hash(bytes, 0);
        Map.Entry<Long, T> entry = nodeHashMap.ceilingEntry(hash);
        if (entry == null) {
            return nodeHashMap.firstEntry().getValue();
        }
        return entry.getValue();
    }


    private void addNode(T node) {
        for (int i = 0; i < virtualReplicas / 4; i++) {
            // 生成md5摘要
            byte[] bytes = md5(node.toString() + i);
            for (int j = 0; j < 4; j++) {
                long hash = hash(bytes, j);
                nodeHashMap.put(hash, node);
            }
        }
    }

    private void removeNode(T node) {
        for (int i = 0; i < virtualReplicas / 4; i++) {
            // 生成md5摘要
            byte[] bytes = md5(node.toString() + i);
            for (int j = 0; j < 4; j++) {
                long hash = hash(bytes, j);
                nodeHashMap.remove(hash);
            }
        }
    }


    private long hash(byte[] digest, int number) {
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[number * 4] & 0xFF))
                & 0xFFFFFFFFL;
    }

    private byte[] md5(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        md5.reset();
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        md5.update(bytes);
        return md5.digest();
    }
}
