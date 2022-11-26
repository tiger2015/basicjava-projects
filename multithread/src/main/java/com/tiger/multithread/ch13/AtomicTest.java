package com.tiger.multithread.ch13;

import java.util.concurrent.TimeUnit;

/**
 * @Author Zenghu
 * @Date 2022年05月29日 17:07
 * @Description
 * @Version: 1.0
 **/
public class AtomicTest {

    private static volatile long total = 0L;

    private static Object lock = new Object();

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        // volatile 不保证原子性
                        //synchronized (lock) {
                            total++;
                        //}
                        try {
                            TimeUnit.MILLISECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " total:" + total);
                }
            }, "thread-" + i).start();

        }
    }

}
