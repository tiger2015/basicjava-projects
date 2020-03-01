package com.tiger.multithread.ch05.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.currentThread;

public class BooleanLock implements Lock {

    private boolean locked = false;
    private Thread currentThread;
    private final List<Thread> blockedList = new ArrayList<>();

    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) {
                final Thread tempThread = currentThread();
                // 添加异常捕获防止被中断导致线程还存在与阻塞线程列表中
                try {
                    if (!blockedList.contains(tempThread)) {
                        blockedList.add(tempThread);
                    }
                    this.wait();  // 释放monitor, 当前线程进入阻塞状态
                } catch (InterruptedException e) {
                    blockedList.remove(tempThread);
                    throw e;
                }
            }
            blockedList.remove(currentThread());
            this.locked = true;
            this.currentThread = currentThread();
        }
    }

    @Override
    public void lock(long milliSeconds) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (milliSeconds < 0) {
                this.lock();
            } else {
                long remain = milliSeconds;
                long endMilliSecond = System.currentTimeMillis() + remain;
                while (locked) {
                    if (remain < 0) {
                        throw new TimeoutException("could't get lock with in time");
                    }
                    if (!blockedList.contains(currentThread())) {
                        blockedList.add(currentThread());
                    }
                    this.wait(remain); // 阻塞指定时间
                    remain = endMilliSecond - System.currentTimeMillis();
                }
                blockedList.remove(currentThread());
                this.locked = true;
                this.currentThread = currentThread();
            }
        }


    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread == currentThread()) {
                this.locked = false;
                this.notifyAll();
            }
        }

    }

    @Override
    public List<Thread> getBlockThreads() {
        return Collections.unmodifiableList(blockedList);
    }
}
