package com.tiger.multithread;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: ExchangerDemo
 * @Author: Tiger
 * @Date: 2023/12/1
 * @Description:
 * @Version: 1.0
 **/
public class ExchangerDemo {

    private static final Exchanger<String> exchanger = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String resA = "A result";
                    exchanger.exchange(resA);
                    System.out.println(Thread.currentThread().getName()+" arrives at syncPoint at " + format.format(LocalDateTime.now()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String resB = "B result";
                   String resF = exchanger.exchange(resB);
                   System.out.println(Thread.currentThread().getName()+" arrives at syncPoint at " + format.format(LocalDateTime.now()));
                   System.out.println("Is the data consistent?" + resF.equals(resB));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.shutdown();
    }


}
