package com.tiger.multithread.ch14;

/**
 * 懒汉模式 + 同步
 */
public final class Singleton3 {
    private byte[] data = new byte[1024];
    private static Singleton3 instance;

    private Singleton3() {

    }

    public synchronized static Singleton3 getInstance() {
        if (instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}
