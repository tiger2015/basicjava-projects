package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.util.concurrent.TimeUnit;

@Slf4j
public class LeaderSelectorWorker extends LeaderSelectorListenerAdapter {
    private static final String LEADER_ROOT = "/workers";
    private String id;
    private CuratorFramework zkClient;
    private LeaderSelector leaderSelector;
    private volatile boolean leader = false;

    public LeaderSelectorWorker(String id) {
        this.id = id;
        zkClient = CuratorFrameworkUtil.getCuratorFramework();
        leaderSelector = new LeaderSelector(zkClient, LEADER_ROOT, this);
        // 保证释放后还能获得领导权
        leaderSelector.autoRequeue();
    }

    public void start() {
        zkClient.start();
        try {
            // 阻塞等待，直到链接成功
            zkClient.blockUntilConnected();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{} start leader select", id);
        leaderSelector.start();
    }

    public void close() {
        leaderSelector.close();
        zkClient.close();
    }


    public void requeue() {
        leaderSelector.requeue();
    }


    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        log.info("{} is leader", id);
        leader = true;
        // 该方法执行完毕后会释放锁，导致从新选举
        TimeUnit.SECONDS.sleep(30);
        leaderSelector.requeue();
    }

    public boolean isLeader() {
        return leader;
    }
}
