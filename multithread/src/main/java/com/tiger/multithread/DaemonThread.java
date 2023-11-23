package com.tiger.multithread;

import sun.misc.Unsafe;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName: DaemonThread
 * @Author: Zeng.h
 * @Date: 2023/11/22
 * @Description:
 * @Version: 1.0
 **/
public class DaemonThread {

    private static boolean ready = false;
    private static int num = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    if (ready) {
                        System.out.println(num + num);
                    }
                    int i = ThreadLocalRandom.current().nextInt(100);
                    System.out.println("random:" + i);
                }
            }
        });

        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 指令重排 影响结果输出
                num = 2;
                ready = true;
                int i = ThreadLocalRandom.current().nextInt(100);
                System.out.println("write:" + i);
            }
        });

        readThread.start();

        writeThread.start();
        TimeUnit.MILLISECONDS.sleep(5);
        readThread.interrupt();

        System.out.println("main thread....");
        LockSupport.park();
        System.out.println("main thread after park");


    }


}
