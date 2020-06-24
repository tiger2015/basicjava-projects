package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.leader.LeaderSelector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LeaderElectTest {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static ScheduledExecutorService schedulerThreadPool = Executors.newScheduledThreadPool(4);

    public static void main(String[] args) throws Exception {
        /**
         for (int i = 0; i < 10; i++) {
         final int id = i;
         threadPool.execute(()->{
         Worker worker = new Worker(id + "");
         try {
         worker.start();
         } catch (Exception e) {
         e.printStackTrace();
         }
         });
         }
         **/

        for (int i = 0; i < 10; i++) {
            LeaderSelectorWorker worker = new LeaderSelectorWorker(i + "");
            threadPool.execute(()->{
                worker.start();
            });
        }
       // TimeUnit.SECONDS.sleep(1000);
    }

}
