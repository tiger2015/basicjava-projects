package com.tiger.pool;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/30 21:06
 * @Description:
 * @Version: 1.0
 **/
public class SlotNotAvailableException extends Throwable {

    public SlotNotAvailableException(RedisNode redisNode, int slot) {
        super(String.format("slot:%d not available on %s", slot, redisNode.getIp() + ":" + redisNode.getPort()));
    }
}
