package com.tiger.multithread.ch05.readwritelock;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReadWriteLockTest {

    private static ExecutorService cacheThreadPool = Executors.newCachedThreadPool();


    public static void main(String[] args) {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        for (int i = 0; i < 65536; i++) {
            cacheThreadPool.submit(() -> {
               for (; ; ) {

                    try {
                        lock.writeLock().lock();
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.writeLock().unlock();
                    }
                }
            });
        }

        cacheThreadPool.shutdown();
        System.out.println("shutdown thread pool");

        /**
         Data data = new Data();

         new Thread(() -> {
         update(data);
         }, "write1").start();
         new Thread(() -> {
         update(data);
         return;
         }, "write2").start();
         **/
    }

    private static void update(Data data) {
        while (true) {
            data.update(Thread.currentThread().getName());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Data {
        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private String msg;

        public void update(String msg) {
            try {
                readWriteLock.writeLock().lock();
                this.msg = msg;
                System.out.println(Thread.currentThread().getName() + " write " + this.msg);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }


        public String get() {
            try {
                readWriteLock.readLock().lock();
                return this.msg;
            } finally {
                readWriteLock.readLock().unlock();
            }
        }
    }

}
