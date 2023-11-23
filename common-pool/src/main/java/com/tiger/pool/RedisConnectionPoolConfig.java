package com.tiger.pool;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/29 21:30
 * @Description:
 * @Version: 1.0
 **/
public class RedisConnectionPoolConfig {
    private int maxTotal = 8;
    private int maxIdle = 8;
    private int minIdle = 4;
    private long maxWaitMills = 3000;


    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWaitMills() {
        return maxWaitMills;
    }

    public void setMaxWaitMills(long maxWaitMills) {
        this.maxWaitMills = maxWaitMills;
    }
}
