package com.tiger.multithread.ch05.concurrent;

/**
 *
 *  wait和notify、notifyAll需要结合synchronized一起使用,
 * 执行这两个方法的前提时必须持有同步方法的monitor所有权，即使用哪个对象的monitor进行同步。
 * 就只能用哪个对象进行wait和notify操作，不然会抛出IllegalMonitorStateException
 *
 */
public class ObjectWaitNotifyTest {

    private static Object object = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            int index = 0;
            synchronized (object) {
                while (index < 10) {
                    try {
                        object.wait();
                        System.out.print("A");
                        index++;
                        object.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();

        new Thread(() -> {
            int index = 0;
            synchronized (object) {
                while (index < 10) {
                    try {
                        object.notify();
                        System.out.print("B");
                        index++;
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
