package com.tiger.multithread;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/11/21 16:59
 * @Description:
 * @Version: 1.0
 **/
public class PrintTest4 {

    public static void main(String[] args) {
        PrintTest4 printTest4 = new PrintTest4();
        printTest4.start();


    }


    public void start() {
        new Thread(new ZeroThread(8)).start();
        new Thread(new OddThread(8)).start();
        new Thread(new EvenThread(8)).start();
    }


    private Object zeroLock = new Object();
    private Object evenLock = new Object();
    private Object oddLock = new Object();

    class ZeroThread implements Runnable {
        private int n;

        public ZeroThread(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            for (int i = 1; i <= n; i++) {
                synchronized (zeroLock) {
                    if (i % 2 == 1) {
                        synchronized (oddLock) {
                            synchronized (evenLock) {
                                System.out.print(0);
                            }
                            oddLock.notifyAll();
                        }
                    } else {
                        synchronized (evenLock) {
                            synchronized (oddLock) {
                                System.out.print(0);
                            }
                            evenLock.notifyAll();
                        }
                    }
                    if (i < n)
                        try {
                            zeroLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }

    class EvenThread implements Runnable {
        private int n;

        public EvenThread(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            for (int i = 2; i <= n; i += 2) {
                synchronized (evenLock) {
                    synchronized (zeroLock) {
                        System.out.print(i);
                        zeroLock.notifyAll();
                    }
                    if (i <= n - 2)
                        try {
                            evenLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }

    class OddThread implements Runnable {
        private int n;

        public OddThread(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            for (int i = 1; i <= n; i += 2) {
                synchronized (oddLock) {
                    synchronized (zeroLock) {
                        System.out.print(i);
                        zeroLock.notifyAll();
                    }
                    if( i <= n - 2)
                    try {
                        oddLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}



