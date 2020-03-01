package com.tiger.multithread.ch07;

import java.util.concurrent.TimeUnit;

/**
 * 首先查找当前线程是否设置了异常处理，如果没有则，查找所在线程组
 * 如果有线程组，则调用线程组的异常处理，如果没有线程组，则查看是否设置了全局默认的异常处理，
 * 如果仍然没有，则直接将异常信息输出到System.err
 */
public class ExceptionHandlerTest {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("group1");


        // 设置全局的默认异常处理
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getThreadGroup().getName()+"-"+t.getName()+" occur exception, user global exception handler");
            e.printStackTrace();
        });

        final Thread thread  = new Thread(group, ()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1 / 0);
        }, "Test");


        // 如果设置了异常处理，则不会调用默认的异常处理
        thread.setUncaughtExceptionHandler((t,e)->{
            System.out.println(t.getThreadGroup().getName()+"-"+t.getName()+" occur exception, use itself exception handler");
            e.printStackTrace();
        });

        thread.start();
    }
}
