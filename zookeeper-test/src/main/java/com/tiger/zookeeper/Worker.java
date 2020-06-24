package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import java.io.IOException;
import java.io.Serializable;

@Slf4j
public class Worker implements LeaderLatchListener, Cloneable, Serializable {
    private static final String LEADER_ROOT = "/workers";
    private String id;
    private CuratorFramework zkClient;
    private LeaderLatch leaderLatch;

    public Worker(String id) {
        this.id = id;
        this.zkClient = CuratorFrameworkUtil.getCuratorFramework();
        leaderLatch = new LeaderLatch(this.zkClient, LEADER_ROOT);
        leaderLatch.addListener(this);
        this.zkClient.start();
    }

    /**
     * 阻塞方法，当选举成为了master后解除阻塞
     * @throws Exception
     */
    public void start() throws Exception {
        this.leaderLatch.start();
        this.leaderLatch.await();
    }


    public void stop() throws IOException {
        this.leaderLatch.close();
        this.zkClient.close();
    }

    @Override
    public void isLeader() {
        log.info("{} is leader", id);
    }

    @Override
    public void notLeader() {
        log.info("{} is not leader", id);
    }
}
