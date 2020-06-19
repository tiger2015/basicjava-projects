package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DistributeLockApp {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private static DistributeLock lock = new DistributeLock("lock0");


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                try{
                    if(lock.lock()){
                        log.info("thread:{} get lock",  Thread.currentThread().getName());
                        try {
                            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 500));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }finally {
                    lock.unlock();
                }
            });
        }
    }
}
