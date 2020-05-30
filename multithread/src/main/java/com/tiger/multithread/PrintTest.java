package com.tiger.multithread;

import java.util.concurrent.atomic.AtomicBoolean;

public class PrintTest {
    static AtomicBoolean lockA = new AtomicBoolean(true);
    static AtomicBoolean lockB = new AtomicBoolean(false);
    static AtomicBoolean lockC = new AtomicBoolean(false);
    static int count = 10;

    public static void main(String[] args) {

        new Thread(new PrintThread("A", lockA, lockB)).start();
        new Thread(new PrintThread("B", lockB, lockC)).start();
        new Thread(new PrintThread("C", lockC, lockA)).start();

    }


    static class PrintThread implements Runnable {
        private String value;
        private AtomicBoolean currentLock;
        private AtomicBoolean nextLock;

        public PrintThread(String value, AtomicBoolean currentLock, AtomicBoolean nextLock) {
            this.value = value;
            this.currentLock = currentLock;
            this.nextLock = nextLock;
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                while (!currentLock.get()) ;
                System.out.print(value);
                currentLock.set(false);
                nextLock.set(true);
            }

        }
    }


}
