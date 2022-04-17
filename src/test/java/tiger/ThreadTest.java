package tiger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/9/2 22:19
 * @Description:
 * @Version: 1.0
 **/
public class ThreadTest {
    private static Object lock = new Object();
    private static Queue<Integer> queue = new LinkedList();

    private static Object lockA = new Object();
    private static Object lockB = new Object();
    private static Object lockC = new Object();

    static class ProducerThread implements Runnable {
        @Override
        public void run() {
            while (true)
                synchronized (lock) {
                    if (queue.isEmpty()) {
                        queue.add(new Integer(1));
                        System.out.println("produce");
                        lock.notify();//
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    static class ConsumerThread implements Runnable {


        @Override
        public void run() {
            while (true)
                synchronized (lock) {
                    if (queue.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        queue.poll();
                        System.out.println("consume");
                        //lock.notify();
                        lock.notifyAll();
                    }
                }
        }
    }


    static class PrintAThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                synchronized (lockC) {
                    synchronized (lockA) {
                        System.out.print("A");
                        lockA.notify();
                    }
                    try {
                        if (i != 2)
                            lockC.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    static class PrintBThread implements Runnable {


        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                synchronized (lockA) {
                    synchronized (lockB) {
                        System.out.print("B");
                        lockB.notify();
                    }
                    try {
                        if (i != 2)
                            lockA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    static class PrintCThread implements Runnable {


        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                synchronized (lockB) {
                    synchronized (lockC) {
                        System.out.print("C");
                        lockC.notify();
                    }
                    if (i != 2) {
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


    public static void main(String[] args) {

        //new Thread(new ProducerThread()).start();

        //new Thread(new ConsumerThread()).start();

        //new Thread(new ConsumerThread()).start();

        new Thread(new PrintAThread(), "PrintA").start();

        new Thread(new PrintBThread(), "PrintB").start();

        new Thread(new PrintCThread(), "PrintC").start();


    }


}
