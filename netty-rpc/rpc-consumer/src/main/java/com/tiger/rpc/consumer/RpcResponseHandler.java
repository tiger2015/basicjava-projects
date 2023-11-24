package com.tiger.rpc.consumer;

import com.tiger.rpc.common.protos.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RpcResponseHandler
 * @Author: Zeng.h
 * @Date: 2023/11/24
 * @Description:
 * @Version: 1.0
 **/
@Slf4j
public class RpcResponseHandler extends ChannelInboundHandlerAdapter {

    private ServiceConsumer.RpcResponseCallback callback;

    public RpcResponseHandler(ServiceConsumer.RpcResponseCallback callback) {
        this.callback = callback;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse response = (RpcResponse) msg;
        log.info("result:{}", response.getStatus());
        callback.callback(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
    }
}
