package com.tiger.redis.lock;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author Zenghu
 * @Date 2021/9/20 16:45
 * @Description
 * @Version: 1.0
 **/
public class RedisLock1 extends RedisLock {
    public RedisLock1(Jedis jedis, String lockKey) {
        super(jedis, lockKey);
    }

    @Override
    public boolean tryLock() {
        // 如果获取锁不成功则，一直阻塞
        while (!lock()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
   public boolean tryLock(long timeout) throws TimeoutException {
        long current = System.currentTimeMillis();
        long end = current + timeout;
        while (!lock()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                current = System.currentTimeMillis();
                if (current > end) { // 抛出异常
                    throw new TimeoutException();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
