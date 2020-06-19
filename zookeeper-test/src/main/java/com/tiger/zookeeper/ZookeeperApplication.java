package com.tiger.zookeeper;


import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
            zooKeeper = new ZooKeeper("192.168.100.201:2181,192.168.100.201:2182", 3000, new ZookeeperWatcher());
        } catch (IOException e) {
            log.error("new zookeeper fail", e);
            zooKeeper = null;
        }
    }


    public static void main(String[] args) throws InterruptedException, KeeperException, NoSuchAlgorithmException {
        //exists("/test/node1");

        //create("/test/node1", "node1", CreateMode.PERSISTENT);
        //update("/test/node1", "node");

        //create("/lock/share_lock", "", CreateMode.PERSISTENT);
        //update("/test/node1", "node");

        create("/test/seq", "", CreateMode.PERSISTENT_SEQUENTIAL);


        // update("/lock/share_lock", "");

        // new Thread(new UpdateThread()).start();
        // new Thread(new UpdateThread()).start();
        // new Thread(new UpdateThread()).start();
        //zooKeeper.close();

       // zooKeeper.close();
        /**
        List<ACL> acls = new ArrayList<>();
        // 采用加密的方式授权，在访问时不需要加密
        String s = DigestAuthenticationProvider.generateDigest("test:test");
        acls.add(new ACL(ZooDefs.Perms.READ, new Id("digest", s)));

        //createWithAcl("/auth_test", "auth_test", CreateMode.PERSISTENT, acls);

        addAuth("digest", "test:test");
        Stat stat = new Stat();
        get("/auth_test", stat);
        log.info(stat.toString());
         **/
    }


    public static void create(String path, String data, CreateMode mode) throws InterruptedException, KeeperException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        }
    }


    public static void createWithAcl(String path, String data, CreateMode mode, List<ACL> acls) throws InterruptedException, KeeperException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            zooKeeper.create(path, data.getBytes(), acls, mode);
        }
    }


    public static void delete(String path) throws InterruptedException, KeeperException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            Stat stat = new Stat();
            String s = get(path, stat);
            if (s != null) {
                zooKeeper.delete(path, stat.getVersion());
            }
        }
    }

    public static void addAuth(String schema, String id) throws InterruptedException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            zooKeeper.addAuthInfo(schema, id.getBytes());
        }
    }

    public static String get(String path, Stat stat) throws InterruptedException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            byte[] data;
            try {
                data = zooKeeper.getData(path, true, stat);
                if (data != null) {
                    return new String(data, StandardCharsets.UTF_8);
                }
            } catch (KeeperException e) {
                log.warn("path:{} data is null", path);
                log.error("occur error", e);
            }
        }
        return null;
    }

    public static List<String> getChildNodes(String path) throws KeeperException, InterruptedException {
        connectSemaphere.await();
        List<String> children = new ArrayList<>();
        if (zooKeeper != null) {
            children = zooKeeper.getChildren(path, true);
        }
        return children;
    }


    public static void update(String path, String data) throws InterruptedException {
        connectSemaphere.await();
        if (zooKeeper != null) {
            Stat stat = new Stat();
            String s = get(path, stat);
            if (s != null) {
                try {
                    zooKeeper.setData(path, data.getBytes(), stat.getVersion());
                } catch (KeeperException e) {
                    log.error("update node fail", e);
                }
            }
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
                } else if (event.getType() == Event.EventType.NodeCreated) {
                    log.info("create node:{}", event.getPath());
                }
            }
        }
    }


    public static class UpdateThread implements Runnable {

        @Override
        public void run() {
            Stat stat = new Stat();
            try {
                synchronized (zooKeeper) {
                    String s = get("/lock/share_lock", stat);
                    int version = stat.getVersion();
                    if (s != null) {
                        get("/lock/share_lock", stat);
                        if (stat.getVersion() == version) {
                            update("/lock/share_lock", "");
                        } else {
                            log.warn("data changed before update,version:{} -> {}", version, stat.getVersion());
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

