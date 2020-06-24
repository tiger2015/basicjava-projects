package com.tiger.multithread.ch05.concurrent;

import java.util.List;

public class Consumer implements Runnable {
    private List<Integer> list;

    public Consumer(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for(;;){
            synchronized (list){
                while (list.size() == 0){
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.remove(0);
                System.out.println(Thread.currentThread().getName()+" consumer 1 message");
                list.notify();
            }
        }
    }
}
