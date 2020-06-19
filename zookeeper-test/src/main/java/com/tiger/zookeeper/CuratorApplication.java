package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CuratorApplication {


    public static void main(String[] args) throws Exception {
        CuratorFramework zkClient = CuratorFrameworkUtil.getCuratorFramework();
        zkClient.start();
       // List<String> list = zkClient.getChildren().forPath("/");
       // for(String path:list){
      //      log.info("{}", path);
      // }
       // log.info("start watch");
        //zkClient.blockUntilConnected();

        // 一次注册，n次监听
        NodeCache nodeCache = new NodeCache(zkClient, "/distribute_lock");
        nodeCache.getListenable().addListener(() -> {
            if(nodeCache.getCurrentData()!=null) {
                log.info("node cache:{}", new String(nodeCache.getCurrentData().getData()));
            }
        });
        //nodeCache.start(true);

        PathChildrenCache childrenCache = new PathChildrenCache(zkClient, "/distribute_lock", true);
        childrenCache.getListenable().addListener((client, event) -> {
            log.info("path children cache:{}:{}", event.getType(), event.getData());
        });
      // childrenCache.start();

        TreeCache treeCache = new TreeCache(zkClient, "/");
        treeCache.getListenable().addListener((client, event) -> {
            log.info("tree cache:{}:{}", event.getType(), event.getData());
        });
        treeCache.start();


        TimeUnit.SECONDS.sleep(10000);
        // 此种方式只监听一次
        /**
         zkClient.getChildren().usingWatcher((Watcher) watchedEvent -> {
            log.info("{}", watchedEvent);
        }).inBackground().forPath("/test");
        **/
        // zkClient.close();
    }
}
