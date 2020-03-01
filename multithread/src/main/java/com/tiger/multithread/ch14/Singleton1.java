package com.tiger.multithread.ch14;

/**
 * 饿汉模式 不存在多线程问题
 */
public final class Singleton1 {
    private byte[] data = new byte[1024];

    private static Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}
