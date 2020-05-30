package com.tiger.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.tiger.netty.entity.CustomMessage;
import com.tiger.netty.entity.MessageCallback;

public class ServerMessageHandler extends ChannelInboundHandlerAdapter {
    private MessageCallback callback;

    public ServerMessageHandler(MessageCallback callback){
        this.callback = callback;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CustomMessage customMessage = (CustomMessage) msg;
        callback.handle(customMessage);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
