package com.tiger.multithread;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: BrokenShutdownThread
 * @Author: Tiger
 * @Date: 2023/11/30
 * @Description:
 * @Version: 1.0
 **/
public class BrokenShutdownThread extends Thread {
    private static volatile boolean on = true;
    private final BlockingQueue<BigInteger> queue;

    public BrokenShutdownThread(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            BigInteger p = BigInteger.ONE;
            while (on && !Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 40; i++) {
                    queue.put(p = p.nextProbablePrime());
                    System.out.println(Thread.currentThread().getName() + ": put value:" + p);
                }
            }
        } catch (InterruptedException e) {
            //Thread.interrupted();
            e.printStackTrace();
        }
    }

    public void cancel() {
        on = false;
        interrupt();
    }

    static class Consumer extends Thread {
        private final BlockingQueue<BigInteger> queue;

        public Consumer(BlockingQueue<BigInteger> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            try {
                while (on && !Thread.currentThread().isInterrupted()) {
                    // 在出现阻塞时，仍需要通过中断结束
                    System.out.println(Thread.currentThread().getName() + ": get value " + queue.take());
                }
                System.out.println("work done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<BigInteger> queue = new LinkedBlockingQueue<>(5);
        BrokenShutdownThread producer = new BrokenShutdownThread(queue);
        producer.start();
        TimeUnit.SECONDS.sleep(1);
        new Consumer(queue).start();
        TimeUnit.SECONDS.sleep(1);
        producer.cancel();
    }

}
