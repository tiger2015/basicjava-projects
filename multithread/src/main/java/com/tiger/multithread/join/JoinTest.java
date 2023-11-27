package com.tiger.multithread.join;

import java.util.concurrent.TimeUnit;

/**
 * @Author Zenghu
 * @Date 2021/12/8 21:49
 * @Description
 * @Version: 1.0
 **/
public class JoinTest {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sub thread exit");

            }
        });
        thread.start();
        try {
            Thread.currentThread().join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main exit");

    }

}
