package com.tiger.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;

public class CuratorFrameworkUtil {
    private static CuratorFrameworkFactory.Builder builder;

    static {
       // RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        RetryPolicy retryPolicy = new RetryForever(5_000);

        builder = CuratorFrameworkFactory.builder();
        builder.retryPolicy(retryPolicy);
        builder.connectionTimeoutMs(10 * 1000);
        builder.sessionTimeoutMs(15*1000);
        builder.connectString("192.168.100.201:2181,192.168.100.201:2182,192.168.100.201:2183");
    }


    public static CuratorFramework getCuratorFramework() {
        return builder.build();
    }
}
