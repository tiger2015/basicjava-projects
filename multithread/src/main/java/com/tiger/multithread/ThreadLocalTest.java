package com.tiger.multithread;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<Integer> local = new ThreadLocal<>();
        IntStream.range(0,10).forEach(i->new Thread(()->{
            try {
                local.set(i);
                System.out.println(currentThread()+" set i "+local.get());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(currentThread() + " get i " + local.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }
}
