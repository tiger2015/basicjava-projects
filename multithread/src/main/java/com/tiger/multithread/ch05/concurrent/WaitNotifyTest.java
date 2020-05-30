package com.tiger.multithread.ch05.concurrent;

import java.util.concurrent.TimeUnit;

public class WaitNotifyTest {
    private static Object lockA = new Object();
    private static Object lockB = new Object();
    private static Object lockC = new Object();

    public static void main(String[] args) throws InterruptedException {
        // test01
        /**
         new Thread(new PrintThread("A", lockA, lockC)).start();
         TimeUnit.MILLISECONDS.sleep(10);
         new Thread(new PrintThread("B", lockB, lockA)).start();
         TimeUnit.MILLISECONDS.sleep(10);
         new Thread(new PrintThread("C", lockC, lockB)).start();
         **/

        new Thread(new MyPrintThread(lockA, lockB, "A")).start();
        TimeUnit.MILLISECONDS.sleep(10);

        new Thread(new MyPrintThread(lockB, lockC, "B")).start();
        TimeUnit.MILLISECONDS.sleep(10);

        new Thread(new MyPrintThread(lockC, lockA, "C")).start();

    }


    static class PrintThread implements Runnable {
        private Object currentLock;
        private Object prevLock;
        private String string;

        public PrintThread(String s, Object currentLock, Object prevLock) {
            this.string = s;
            this.currentLock = currentLock;
            this.prevLock = prevLock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (prevLock) {
                    synchronized (currentLock) {
                        System.out.print(string);
                        currentLock.notifyAll();
                    }
                    try {
                        if (i == 9) {
                            prevLock.notifyAll();
                        } else {
                            prevLock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    static class MyPrintThread implements Runnable {
        private Object lock;
        private Object nextLock;
        private String value;

        public MyPrintThread(Object lock, Object nextLock, String value) {
            this.lock = lock;
            this.nextLock = nextLock;
            this.value = value;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++)
                synchronized (lock) {
                    lock.notify();
                    synchronized (nextLock) {
                        System.out.print(value);
                        try {
                            nextLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }
}
