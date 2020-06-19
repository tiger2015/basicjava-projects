package com.tiger.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DistributeLock {

    private String lockName;
    private InterProcessMutex interProcessMutex; // 可重入锁
    private static CuratorFramework curatorFramework;
    private static final String LOCK_ROOT = "/distribute_lock/";

    static {
        curatorFramework = CuratorFrameworkUtil.getCuratorFramework();
        curatorFramework.start();
    }

    public DistributeLock(String lockName) {
        this.lockName = lockName;
        this.interProcessMutex = new InterProcessMutex(curatorFramework, LOCK_ROOT + lockName);
    }

    /**
     * 每次获取锁时等待一秒，直到获取到锁
     *
     * @return
     */
    public boolean lock() {
        while (true) {
            try {
                if (interProcessMutex.acquire(100, TimeUnit.MILLISECONDS)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 等待指定时间后，如果不能获取锁，则退出等待
     *
     * @param time
     * @param timeUnit
     * @return
     * @throws Exception
     */
    public boolean lock(long time, TimeUnit timeUnit) throws Exception {
        return interProcessMutex.acquire(time, timeUnit);
    }


    public void unlock() {
        if (interProcessMutex.isAcquiredInThisProcess()) {
            try {
                interProcessMutex.release();
                curatorFramework.delete().inBackground().forPath(LOCK_ROOT + lockName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
