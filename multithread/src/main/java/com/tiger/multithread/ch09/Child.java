package com.tiger.multithread.ch09;

import java.util.Random;

public class Child extends Parent {
    public static int y = 10;
    public static final int LEN = 10;

    public static final int RADOM = new Random().nextInt();

    static {
        System.out.println("init child");
    }

}
