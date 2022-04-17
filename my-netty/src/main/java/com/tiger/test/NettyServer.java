package com.tiger.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author Zenghu
 * @Date 2021/7/14 22:42
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class NettyServer {


    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        ServerBootstrap server = new ServerBootstrap();

        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageHandler());
                    }
                });
        try {
            log.info("start server");
            ChannelFuture future = server.bind(9999).sync();
            future.channel().closeFuture().sync();
            log.info("shutdown server");
        } catch (Exception e) {
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


    private static class MessageHandler extends ChannelInboundHandlerAdapter {


        public MessageHandler() {
            log.info("create message handler");
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("new connection:{}", ctx.channel().remoteAddress());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buffer = (ByteBuf) msg;
            int len = buffer.readableBytes();
            if (len > 0) {
                byte[] array = new byte[len];
                buffer.readBytes(array);
                log.info("receive:{}", new String(array, CharsetUtil.UTF_8));
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

            ctx.channel().eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    //
                }
            });

            ctx.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {

                }
            }, 3000, TimeUnit.MILLISECONDS);



            ctx.writeAndFlush(Unpooled.wrappedBuffer("hello".getBytes(CharsetUtil.UTF_8)));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
