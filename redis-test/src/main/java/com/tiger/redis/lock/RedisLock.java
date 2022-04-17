package com.tiger.redis.lock;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeoutException;

/**
 * @Author Zenghu
 * @Date 2021/9/20 16:42
 * @Description
 * @Version: 1.0
 **/
public abstract class RedisLock {

    protected Jedis jedis;
    protected String lockKey;

    public RedisLock(Jedis jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
    }

    protected boolean lock() {
        return jedis.setnx(lockKey, "") > 0;
    }

    public abstract boolean tryLock();

    public abstract boolean tryLock(long timeout) throws TimeoutException;

    public boolean unlock() {
        return jedis.del(lockKey) > 0L;
    }
}
