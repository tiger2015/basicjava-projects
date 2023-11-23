package com.tiger.multithread.ch14;

/**
 * 双重检测
 */
public final class Singleton4 {
    private byte[] data = new byte[1024];

    private static Singleton4 instance;

    private Singleton4() {
    }


    public static Singleton4 getInstance() {
        if (instance == null) {

            synchronized (Singleton4.class) {

                if (instance == null) {
                    instance = new Singleton4();
                }

            }
        }
        return instance;
    }

}
