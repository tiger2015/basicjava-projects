package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DistributeLockApp {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);
    //private static DistributeLock lock = new DistributeLock("lock0");


    static class TigerThread implements Runnable {
        private TigerDistributeLock lock = new TigerDistributeLock();


        @Override
        public void run() {
            try {
                lock.lock();
                log.info("thread:{} get lock", Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep((long) (3000 * Math.random()));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {

        for (int i = 0; i < 5; i++)
            new Thread(new TigerThread()).start();


    }
}
