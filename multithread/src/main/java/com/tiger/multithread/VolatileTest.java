package com.tiger.multithread;

import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;

public class VolatileTest {
    final static int MAX = 5;
    static volatile int init_value = 0;


    public static void main(String[] args) {

        // 读线程
        new Thread(() -> {
            int localValue = init_value;
            while (localValue < MAX) {
                if (init_value != localValue) {
                    System.out.println("init value update to:" + init_value);
                    init_value = localValue;
                }

            }
//            while(init_value < MAX){
//                System.out.println("init value update to:" + init_value);
//            }

        }, "Reader").start();

        // 写线程
        new Thread(() -> {
            int localValue = init_value;
            while (localValue < MAX) {
                System.out.println("init value changed to:" +  ++localValue);
                init_value = localValue;

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "Writer").start();






    }



}
