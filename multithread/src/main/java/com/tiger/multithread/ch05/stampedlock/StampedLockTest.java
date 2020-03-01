package com.tiger.multithread.ch05.stampedlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

    public static void main(String[] args) {
        Data data = new Data();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    String read = data.read();
                    System.out.println(Thread.currentThread().getName() + " read " + read);
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Read Thread " + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    data.write(j + "");
                    System.out.println(Thread.currentThread().getName() + " write " + j);
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Write Thread " + i).start();
        }


    }
}


class Data {
    private String data;
    final StampedLock stampedLock = new StampedLock();


    public String read() {

        long stamp = stampedLock.tryOptimisticRead();
        String tmp = data;
        if (!stampedLock.validate(stamp)) { //在读的过程中发生修改
            stamp = stampedLock.readLock();
            try {
                tmp = data;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        return tmp;
    }

    public void write(String data) {
        long stamp = stampedLock.writeLock();
        try {
            this.data = data;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}
