package com.tiger.multithread.ch05.concurrent;

import java.util.ArrayList;
import java.util.List;

public class Producer implements Runnable {
    private List<Integer> list;
    private static final int SIZE = 1;

    public Producer(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (;;){
            synchronized (list){
                while (list.size() == SIZE){
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(1);
                System.out.println(Thread.currentThread().getName()+" produce 1 message");
                list.notify();
            }
        }
    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        // 会产生死锁，因为消费者和生产者的等待条件不一样，消费者等待队列为满，生产者等待队列为空
        Consumer consumer1 = new Consumer(list);
        Consumer consumer2 = new Consumer(list);
        Producer producer1 = new Producer(list);
        Producer producer2 = new Producer(list);

        new Thread(consumer1).start();
       // new Thread(consumer2).start();
        new Thread(producer1).start();
        new Thread(producer2).start();

    }

}
