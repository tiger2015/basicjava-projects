package com.tiger.multithread.ch03;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadYieldTest {


    public static void main(String[] args) {
       // IntStream.range(0, 2).mapToObj(ThreadYieldTest::create).forEach(Thread::start);

        Thread.currentThread().interrupt();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("first interrupt");
        }

        // 清除中断标识
        System.out.println("Main thread is interrupted? " + Thread.interrupted());
        System.out.println("Main thread is interrupted? " + Thread.currentThread().isInterrupted());
        Thread.currentThread().interrupt();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }


    }


    private static Thread create(int index) {
        return new Thread(() -> {
            if (index == 0) {
                Thread.yield();
            }
            // System.out.println(Thread.currentThread().getName());
            System.out.println(index);
        });
    }
}
