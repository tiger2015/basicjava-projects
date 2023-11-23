package com.tiger.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/29 20:45
 * @Description:
 * @Version: 1.0
 **/
@Slf4j
public class RedisClusterConfig {
    private String password = "";
    private int db = 0;
    private long maxWaitMills = 3000;
    private List<Node> nodes = new ArrayList<>();

   static class Node {
        public final String host;
        public final int port;

        public Node(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public long getMaxWaitMills() {
        return maxWaitMills;
    }

    public void setMaxWaitMills(long maxWaitMills) {
        this.maxWaitMills = maxWaitMills;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
