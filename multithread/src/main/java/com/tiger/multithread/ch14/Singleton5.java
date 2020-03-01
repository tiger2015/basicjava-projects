package com.tiger.multithread.ch14;

/**
 * 加入关键字volatile
 */
public final class Singleton5 {
    private byte[] data = new byte[1024];

    private volatile static Singleton5 instance;

    private Singleton5() {
    }


    public static Singleton5 getInstance() {
        if (instance == null) {

            synchronized (Singleton5.class) {

                if (instance == null) {
                    instance = new Singleton5();
                }

            }
        }
        return instance;
    }

}
