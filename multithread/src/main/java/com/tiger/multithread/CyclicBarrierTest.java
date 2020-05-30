package com.tiger.multithread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    private static int threadCount = 3;
    // 达到同一点后开始执行
    // 可重用
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount, () -> {
        System.out.println("execute barrier mtehod");
    });

    public static void main(String[] args) {
        for (int i = 0; i < threadCount - 1; i++) {
            new Thread(new MyRunnable(), "thread-" + i).start();
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("main finish");
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " start wait");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finish");
        }
    }


}
