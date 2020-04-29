package com.tiger.zookeeper;


import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName ZookeeperApplication
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/4/28 9:53
 * @Version 1.0
 **/
@Slf4j
public class ZookeeperApplication {
    private static CountDownLatch connectSemaphere = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("192.168.200.201:2181,192.168.200.201:2182", 3000, new ZookeeperWatcher());
        } catch (IOException e) {
            log.error("new zookeeper fail", e);
            zooKeeper = null;
        }
    }


    public static void main(String[] args) throws KeeperException, InterruptedException {
        exists("/test/node1");
        //create("/test/node1", "node1", CreateMode.PERSISTENT);
         update("/test/node1", "node");

         zooKeeper.close();

    }


    public static void create(String path, String data, CreateMode mode) throws InterruptedException, KeeperException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        }
    }


    public static void delete(String path) throws InterruptedException, KeeperException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            zooKeeper.delete(path, 0);
        }
    }


    public static String get(String path, Stat stat) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            byte[] data = zooKeeper.getData(path, true, stat);
            if (data != null) {
                return new String(data, "UTF-8");
            }
        }
        return null;
    }

    public static void update(String path, String data) throws InterruptedException, KeeperException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            zooKeeper.setData(path, data.getBytes(), 0);
        }
    }

    public static void exists(String path) throws InterruptedException, KeeperException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            zooKeeper.exists(path, true);
        }
    }

    private static class ZookeeperWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            if (event.getState() == Event.KeeperState.SyncConnected) {
                if (event.getType() == Event.EventType.None) {
                    connectSemaphere.countDown();
                    log.info("connect success");
                } else if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
                    log.info("node:{} data changed", event.getPath());
                } else if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                    log.info("node:{} delete", event.getPath());
                } else if(event.getType() == Event.EventType.NodeCreated){
                    log.info("create node:{}", event.getPath());
                }
            }
        }
    }
}
