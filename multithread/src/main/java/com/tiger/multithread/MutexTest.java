package com.tiger.multithread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MutexTest {
    private static Mutex mutex = new Mutex();
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            new Thread(new MyTask()).start();


    }


    private static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                if (!reentrantLock.tryLock(3, TimeUnit.SECONDS)) {
                    System.out.println("could't acquire lock");
                    return;
                }
                System.out.println("acquire lock");
                TimeUnit.SECONDS.sleep((long) (Math.random() * 5));
            } catch (InterruptedException e) {
                System.out.println("get lock fail");
                e.printStackTrace();
            } finally {
                // 如果没有获得锁时调用释放锁，会出现illegal monitor exception
                  if(reentrantLock.isHeldByCurrentThread())
                    reentrantLock.unlock();
            }
        }
    }
}
