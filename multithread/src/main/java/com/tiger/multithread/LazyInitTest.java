package com.tiger.multithread;

import java.util.concurrent.TimeUnit;

/**
 * @Author Zenghu
 * @Date 2021/9/25 17:07
 * @Description
 * @Version: 1.0
 **/
public class LazyInitTest {

    private volatile String msg;

    public String getMsg() {
        /**
         String result = msg;
         if (result == null) {
         synchronized (this) {
         result = msg;
         if (result == null) {
         msg = result = computeValue();
         }
         }
         }
         return result;
         **/
        if (msg == null) {
            synchronized (this) {
                if (msg == null)
                    msg = computeValue();
            }
        }
        return msg;

    }


    private String computeValue() {

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }


    public static void main(String[] args) {
        LazyInitTest test = new LazyInitTest();
        System.out.println(System.nanoTime());
        for (int i = 0; i < 100000; i++) {
            test.getMsg();
        }
        System.out.println(System.nanoTime());


    }


}
