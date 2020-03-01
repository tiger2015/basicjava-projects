package com.tiger.multithread.ch14;

/**
 * 懒汉模式，多线程情况下存在初始化多次
 */
public final class Singleton2 {
    private byte[] data = new byte[1024];

    private static Singleton2 instance;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}
