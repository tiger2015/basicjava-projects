package com.tiger.multithread;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/11/21 16:15
 * @Description:
 * @Version: 1.0
 **/
public class PrintTest3 {

    private static Object lockA = new Object();
    private static Object lockB = new Object();
    private static Object lockC = new Object();


    public static void main(String[] args) {
        new Thread(new PrintAThread()).start();
        new Thread(new PrintBThread()).start();
        new Thread(new PrintCThread()).start();


    }


    static class PrintAThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (lockA) {
                    synchronized (lockB) {
                        System.out.print("A");
                        lockB.notifyAll();
                    }
                    lockA.notifyAll();
                    if (i < 9) {
                        try {
                            lockA.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class PrintBThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (lockB) {
                    synchronized (lockC) {
                        System.out.print("BB");
                        lockC.notifyAll();
                    }
                    if (i < 9) {
                        try {
                            lockB.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    static class PrintCThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (lockC) {
                    synchronized (lockA) {
                        System.out.print("CCC");
                        lockA.notifyAll();
                    }
                    if (i < 9) {
                        try {
                            lockC.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }
}
