package com.tiger.redis.lock;

import redis.clients.jedis.Jedis;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zenghu
 * @Date 2021/9/20 17:02
 * @Description
 * @Version: 1.0
 **/
public class LockTest {

    private static int index = 0;

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 4, 120, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024));

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            Jedis jedis = new Jedis("192.168.100.3", 6379);
            jedis.auth("tiger");
            RedisLock lock = new RedisLock1(jedis,"lock");
            threadPool.execute(new Task(lock));
        }
        TimeUnit.SECONDS.sleep(6);
        System.out.println("index = " + index);
        threadPool.shutdown();
    }


    static class Task implements Runnable {
        private RedisLock lock;

        public Task(RedisLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                if(lock.tryLock()){
                    TimeUnit.MILLISECONDS.sleep(50);
                    index++;
                }
                /**
                TimeUnit.MILLISECONDS.sleep(50);
                index++;
               **/
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                //System.out.println("释放锁");
            }
        }
    }


}
