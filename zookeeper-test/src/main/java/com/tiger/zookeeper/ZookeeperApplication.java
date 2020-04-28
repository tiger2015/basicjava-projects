package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZookeeperApplication {
     private static CountDownLatch connectedSemaphere = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 采用的是异步非阻塞方式
        ZooKeeper zooKeeper = new ZooKeeper("192.168.100.201:2181,192.168.100.201:2182", 3000, event -> {
            if(event.getState() == Watcher.Event.KeeperState.SyncConnected){
                connectedSemaphere.countDown();
            }
        });
        // 需要判断连接是否成功
        connectedSemaphere.await();
        ZooKeeper.States state = zooKeeper.getState();
        log.info("state:{}", state.isConnected());
        zooKeeper.create("/test", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.close();
    }

}
