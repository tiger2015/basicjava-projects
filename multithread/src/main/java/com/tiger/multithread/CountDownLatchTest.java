package com.tiger.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    private static int threadCount = 3;
    // 所有执行完毕后开关打开
    private static CountDownLatch downLatch = new CountDownLatch(threadCount);

    public static void main(String[] args) {
        for (int i = 0; i < threadCount; i++) {
            new Thread(new MyRunnable(), "thread-" + i).start();
        }
        try {
            System.out.println("waiting for");
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }


    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " finished");
                downLatch.countDown();
            }
        }
    }
}
