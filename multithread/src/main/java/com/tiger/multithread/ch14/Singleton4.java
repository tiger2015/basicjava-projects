package com.tiger.multithread.ch14;

/**
 * 双重检测
 * 可能产生还未初始化的对象被使用
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
                    // 1.对象分配内存
                    // 2.对象初始化
                    // 3.赋值
                    // 正常顺序：1->2->3
                    // 如果发生指令重排：1->3->2
                    instance = new Singleton4();
                }
            }
        }
        return instance;
    }

}
