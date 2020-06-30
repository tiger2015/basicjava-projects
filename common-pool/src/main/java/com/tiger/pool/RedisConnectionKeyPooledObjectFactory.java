package com.tiger.pool;

import io.lettuce.core.RedisClient;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/29 21:44
 * @Description:
 * @Version: 1.0
 **/
public class RedisConnectionKeyPooledObjectFactory implements KeyedPooledObjectFactory<String, RedisConnection> {
    private RedisClusterInfo redisCluster;

    public RedisConnectionKeyPooledObjectFactory(RedisClusterInfo redisCluster) {
        this.redisCluster = redisCluster;
    }

    /**
     * 创建对象，如果对象池中没有空闲对象则会调用这个方法
     *
     * @return
     * @throws Exception
     */

    @Override
    public PooledObject<RedisConnection> makeObject(String key) throws Exception {
        RedisClient redisClient = redisCluster.getRedisClient(key);
        return new DefaultPooledObject<>(new RedisConnection(key, redisClient));
    }

    /**
     * 销毁对象 当检测到某个空闲对象的空闲时间超时时，或者使用完对象归还到对象池之前被检测到对象已经无效时，会调用此方法销毁对象
     *
     * @param p
     * @throws Exception
     */
    @Override
    public void destroyObject(String key, PooledObject<RedisConnection> p) throws Exception {
        p.getObject().close();
    }

    /**
     * 检测一个对象是否有效，在获取对象或者归还对象到对象池中时，会调用此方法
     *
     * @param p
     * @return
     */

    @Override
    public boolean validateObject(String key, PooledObject<RedisConnection> p) {
        return p.getObject().isConnected();
    }

    /**
     * 激活对象或者说启动对象的某些操作
     *
     * @param p
     * @throws Exception
     */
    @Override
    public void activateObject(String key, PooledObject<RedisConnection> p) throws Exception {
        p.getObject().connect();
    }

    /**
     * 钙化一个对象，向对象池中归还一个对象时会调用此方法，对对象进行清理操作
     *
     * @param p
     * @throws Exception
     */
    @Override
    public void passivateObject(String key, PooledObject<RedisConnection> p) throws Exception {

    }
}
