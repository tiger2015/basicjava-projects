package com.tiger.multithread;

import java.util.concurrent.TimeUnit;

public class SharedResourceTest {

    static int id = 0;

    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                System.out.println("read id:" + id);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }, "read").start();


        new Thread(() -> {
            while (true) {
                id += 500;
                id = id % 1024;
                System.out.println("write id:" + id);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "write").start();
    }
}
