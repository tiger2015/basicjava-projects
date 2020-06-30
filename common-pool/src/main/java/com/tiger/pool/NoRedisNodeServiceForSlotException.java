package com.tiger.pool;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/30 21:17
 * @Description:
 * @Version: 1.0
 **/
public class NoRedisNodeServiceForSlotException extends Throwable {
    public NoRedisNodeServiceForSlotException(int slot) {
        super("no redis service for slot:" + slot);
    }
}
