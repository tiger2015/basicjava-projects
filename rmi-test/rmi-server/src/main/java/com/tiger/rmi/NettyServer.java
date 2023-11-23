package com.tiger.rmi;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.Charset;

public class NettyServer {
    private static EventLoopGroup boss = new NioEventLoopGroup(4);
    private static EventLoopGroup worker = new NioEventLoopGroup(8);


    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageHandler());
                    }
                });
        ChannelFuture future = bootstrap.bind(9000);
        try {
            future.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class MessageHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            System.out.println("channel inactive");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buffer = (ByteBuf) msg;
            int len = buffer.readableBytes();
            byte[] message = new byte[len];
            buffer.readBytes(message);
            System.out.println(new String(message, "UTF-8"));
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello", Charset.forName("UTF-8")));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }

}
