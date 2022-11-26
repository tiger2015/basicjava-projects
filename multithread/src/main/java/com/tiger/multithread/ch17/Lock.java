package com.tiger.multithread.ch17;

/**
 * @Author Zenghu
 * @Date 2022年06月04日 16:49
 * @Description
 * @Version: 1.0
 **/
public interface Lock {

    void lock() throws InterruptedException;

    void unlock();
}
