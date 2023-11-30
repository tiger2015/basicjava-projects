package com.tiger.multithread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName: EarlySignalDemo
 * @Author: Tiger
 * @Date: 2023/11/30
 * @Description:
 * @Version: 1.0
 **/
public class EarlySignalDemo {

    private List<String> list;
    private static final DateFormat format = new SimpleDateFormat("HH:mm:ss");
    private AtomicLong number = new AtomicLong();

    public EarlySignalDemo() {
        this.list = new ArrayList<>();
    }

    public void remove() throws InterruptedException {
        synchronized (list) {
            /**
             if (list.isEmpty()) {
             System.out.println(Thread.currentThread().getName() + " wait");
             list.wait();
             }
             **/
            while (list.isEmpty()) {
                list.wait();
            }
            String item = list.remove(0);
            System.out.println(Thread.currentThread().getName() + ": remove element:" + item + " ! " + format.format(new Date()));
        }
    }

    public void add() {
        synchronized (list) {
            list.add("" + number.incrementAndGet());
            System.out.println(Thread.currentThread().getName() + ": add item " + number.get() + " " + format.format(new Date()));
            list.notifyAll();
        }
    }


    static class AddThread implements Runnable {
        private EarlySignalDemo es;

        public AddThread(EarlySignalDemo es) {
            this.es = es;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(600);
                es.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    static class RemoveThread implements Runnable {
        private EarlySignalDemo es;

        public RemoveThread(EarlySignalDemo es) {
            this.es = es;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                es.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EarlySignalDemo es = new EarlySignalDemo();
        for (int i = 0; i < 3; i++) {
            new Thread(new RemoveThread(es), "RemoveThread-" + i).start();
        }
        new Thread(new AddThread(es), "AddThread").start();
    }

}
