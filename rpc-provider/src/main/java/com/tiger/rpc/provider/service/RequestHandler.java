package com.tiger.rpc.provider.service;

import com.tiger.rpc.common.RpcRequest;
import com.tiger.rpc.common.RpcResponse;
import com.tiger.rpc.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName RequestHandler
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/12 20:34
 * @Version 1.0
 **/
@Slf4j
public class RequestHandler extends ChannelInboundHandlerAdapter {
    private UserService userService = new UserServiceImpl();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            RpcRequest request = (RpcRequest) msg;
            Class<?> clazz = Class.forName(request.getClassName());
            Method method = clazz.getMethod(request.getMethodName(), request.getTypes());
            Object result = method.invoke(userService, request.getParams());
            RpcResponse response = new RpcResponse();
            response.setResult(result);
            ctx.channel().writeAndFlush(response);
        } catch (Exception e) {
            log.error("invoke method occur error", e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.warn("channel inactive");
    }
}
