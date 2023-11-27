package com.tiger.multithread.pool;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author Zenghu
 * @Date 2022年04月17日 17:17
 * @Description
 * @Version: 1.0
 **/
public class PoolTest {

    static AtomicLong index = new AtomicLong(0);

    public static void main(String[] args) {

        ThreadGroup group = new ThreadGroup("test");

        ExecutorService threadPool = new ThreadPoolExecutor(4, 4, 120, TimeUnit.SECONDS, new LinkedBlockingQueue<>(24), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(group, r, "task-"+index.getAndIncrement());
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                Runnable poll = executor.getQueue().poll();
                Task task = (Task) poll;
                System.out.println("poll task:" + task.id);
                executor.execute(r);
            }
        });


        for (int i = 0; i < 100; i++) {
            try {
                // 拒绝策略会抛出异常
                threadPool.execute(new Task(i));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    private static class Task implements Runnable {

        private int id = 0;

        public Task(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("task-id:" + id+", thread name:" + Thread.currentThread().getName());
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
