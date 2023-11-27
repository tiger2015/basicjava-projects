package com.tiger.zookeeper;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/9/6 15:57
 * @Description:
 * @Version: 1.0
 **/
public class TigerDistributeLock implements Watcher {
    public static final Logger log = LoggerFactory.getLogger(TigerDistributeLock.class);
    private static String lockName = "/lock";
    private ZooKeeper zooKeeper;

    public TigerDistributeLock() {
        try {
            //log.info("start create zookeeper");
            zooKeeper = TigerZookeeperFactory.build();
            while (!zooKeeper.getState().isConnected());
            zooKeeper.register(this);
            //log.info("create zookeeper");
        } catch (Exception e) {
            log.info("create zookeeper fail", e);
        }
    }

    public boolean lock(long timeout) throws Exception {
        if (zooKeeper == null) return false;
        try {
            String s = zooKeeper.create(lockName+"/", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            if (s == null) return false;
            String current = s.substring(s.lastIndexOf("/") + 1);
            log.info("current lock:{}", current);
            List<String> children = zooKeeper.getChildren(lockName, null);
            if (children.size() == 1) {  // 加锁成功，当前只有一个线程加锁
                return true;
            }
            Collections.sort(children); // 按照序号从小到大排序
            // 查找第一个比当前小的节点并监听
            String monitor = current;
            for (String child : children) {
                if (current.compareTo(child) > 0) {
                    monitor = child;
                }
            }
            if(monitor.equals(current)) return true;
            log.info("monitor node:{}", monitor);
            synchronized (this) {
                zooKeeper.exists(lockName + "/" + monitor, this);
                this.wait(timeout);
            }
            return true;
        } catch (KeeperException | InterruptedException e) {
            throw new Exception("lock fail", e);
        }
    }

    public boolean lock() throws Exception {
        return lock(0);
    }


    public void unlock() {
        if (zooKeeper != null) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDeleted) {
            synchronized (this) {
                this.notify();
            }
        }
    }
}
