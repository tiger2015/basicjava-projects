package com.tiger.rpc.consumer;

import com.tiger.rpc.common.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName ResponseHandler
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/12 20:25
 * @Version 1.0
 **/
@Slf4j
public class ResponseHandler extends ChannelInboundHandlerAdapter {
    private ServiceConsumer consumer;

    public ResponseHandler(ServiceConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        consumer.channelActive();
        log.info("channel active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse response = (RpcResponse) msg;
        log.info("receive response:{}", response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("channel occure error", cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("channel inactive");
        consumer.channelInactive();
        ctx.channel().eventLoop().schedule(() -> {
            log.info("consumer reconnect");
            consumer.close();
            consumer.connect();
        }, 1, TimeUnit.SECONDS);
    }
}
