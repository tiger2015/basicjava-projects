package com.tiger.multithread.ch05.concurrent;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface Lock {

    void lock() throws InterruptedException; // 没有超时

    void lock(long milliSeconds) throws InterruptedException, TimeoutException; // 有超时

    void unlock();

    List<Thread> getBlockThreads(); //  返回被阻塞的线程

}
