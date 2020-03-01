package com.tiger.multithread.ch05.concurrent;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 使用notify、wait定义阻塞队列
 *
 * @param <T>
 */
public class EventQueue<T> {
    private final int capacity;
    private LinkedList<T> queque;

    public EventQueue(int capacity) {
        queque = new LinkedList<>();
        this.capacity = capacity;
    }

    public void put(T event) {
        synchronized (queque) {
            //  ==============
            //  采用循环判断，避免队满后仍然向队列中添加元素
            //  多线程操作时，可能多个线程执行了wait方法，然后被唤醒，此时不会再去判断队列长度，直接向队列中添加元素
            //  =============
            while (queque.size() > capacity) {
                try {
                    queque.wait();
                    System.out.println("the queue is full");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 向队列尾部添加元素
            queque.addLast(event);
            System.out.println("add:" + event.toString());
            // ============
            // 注意：notify 与 notifyAll
            // =================
            queque.notifyAll();
            //queque.notify();
        }
    }

    public T offer() {
        synchronized (queque) {
            while (queque.size() <= 0) {
                try {
                    System.out.println("the queue is empty");
                    queque.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T event = queque.removeFirst();
            queque.notifyAll();
            // queque.notify();
            return event;
        }
    }


    public static void main(String[] args) {
        EventQueue<String> queue = new EventQueue<>(12);

        new Thread(() -> {
            int index = 0;
            while (true) {
                queue.put("item-" + index++);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        IntStream.range(0, 2).forEach(i ->
                new Thread(() -> {
                    while (true) {
                        String item = queue.offer();
                        System.out.println("get:" + item);
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start()
        );


    }

}
