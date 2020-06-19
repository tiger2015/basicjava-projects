package com.tiger.multithread.ch08;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class ThreadPoolTest {
    // 只有一个线程的线程池
    // 核心线程数1， 最大线程数1
    // 最大任务数：Integaer.MAX_VALUE
    // 拒绝策略：直接抛弃任务
    private static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    // 线程数固定的线程池
    // 核心线程n, 最大线程n
    // 最大任务数： Integer.MAX_VALUE
    // 拒绝策略：直接抛弃任务
    private static ExecutorService fixThreadPool = Executors.newFixedThreadPool(2);


    // 核心线为0， 最大线程数为Integaer.MAX_VALUE
    // 60秒检查一次是否回收空闲线程
    private static ExecutorService cacheThreadPool = Executors.newCachedThreadPool();


    // 执行定时任务的线程池
    // 核心线程数1， 最大线程数1
    private static ScheduledExecutorService singleScheduleThreadPool = Executors.newSingleThreadScheduledExecutor();

    private static ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(4);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // singleThreadPool.execute(ThreadFactory.createTask());
        // singleThreadPool.execute(ThreadFactory.createTask());

        // singleScheduleThreadPool.scheduleAtFixedRate(ThreadFactory.createTask(), 0, 1, TimeUnit.SECONDS);

        MyTask task = new MyTask();
        MyFutureTask futureTask = new MyFutureTask(task);
        fixThreadPool.execute(futureTask);
        while (!futureTask.isDone()){

        }
        System.out.println(futureTask.get());

        //String s = future.get();
       // System.out.println(s);


    }


    static class ThreadFactory {
        static Runnable createTask() {
            return () -> {
                System.out.println(Instant.now());
                System.out.println("=====");
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };


        }


    }


    static class MyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(5);
            return "hello";
        }
    }

    static class MyFutureTask extends FutureTask<String> {

        public MyFutureTask(Callable<String> callable) {
            super(callable);
        }
    }

}
