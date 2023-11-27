package com.tiger.multithread.ch12;

import java.util.concurrent.TimeUnit;

public class VolatileFoo {

    final static int MAX = 5;
    static volatile int initValue = 0;


    public static void main(String[] args) {
        new Thread(() -> {

            int localValue = initValue;
            while (localValue < MAX) {
                if (initValue != localValue) {
                    System.out.printf("the init value update to [%d]\n", initValue);
                    localValue = initValue;
                }
            }

            /**
            while (initValue < MAX){

                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.printf("read init value [%d]\n", initValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
             **/


        }, "Reader").start();

        new Thread(() -> {
            int localValue = initValue;
            while (localValue < MAX) {
                System.out.printf("the init value will be changed to [%d]\n", ++localValue);
                initValue = localValue;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Updater").start();
    }
}
