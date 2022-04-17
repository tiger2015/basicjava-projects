package com.tiger.zookeeper;

import org.apache.curator.utils.DefaultZookeeperFactory;
import org.apache.zookeeper.ZooKeeper;

import static org.apache.zookeeper.client.ZooKeeperSaslClient.ENABLE_CLIENT_SASL_KEY;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/9/6 15:59
 * @Description:
 * @Version: 1.0
 **/
public class TigerZookeeperFactory {

    private static DefaultZookeeperFactory factory = new DefaultZookeeperFactory();
    private static String connectString = "192.168.100.101:2181,192.168.100.102:2181,192.168.100.103:2181";
    private static int sessionTimeout = 3000;

    // private static ZKClientConfig clientConfig = new ZKClientConfig();
    static {
        //clientConfig.setProperty("zookeeper.sasl.client", "false");
        System.setProperty(ENABLE_CLIENT_SASL_KEY, "false");
    }

    public static ZooKeeper build() throws Exception {
        //return  new ZooKeeper(connectString, sessionTimeout, (event)->{}, clientConfig);

        return factory.newZooKeeper(connectString, sessionTimeout, null, false);
    }

}
