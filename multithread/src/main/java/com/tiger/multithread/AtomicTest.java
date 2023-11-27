package com.tiger.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/11/17 22:59
 * @Description:
 * @Version: 1.0
 **/
public class AtomicTest {
    private static AtomicLong atomicLong = new AtomicLong();
    private static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(8);
    private static Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            threadPool.scheduleAtFixedRate(new WriteTask(), i, 1000, TimeUnit.MILLISECONDS);
        }
    }


    static class WriteTask implements Runnable {

        public WriteTask() {
        }

        @Override
        public void run() {
            long l = System.currentTimeMillis() / 1000;
            long andSet = atomicLong.getAndSet(l);
            if (andSet != l && l % 5 == 0) {
                System.out.println(l + ":" + Thread.currentThread().getName() + " write");
            }

        }
    }

}
