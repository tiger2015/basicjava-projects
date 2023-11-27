package com.tiger.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Zenghu
 * @Date 2021/7/14 22:56
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        Bootstrap client = new Bootstrap();


        client.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageHandler());
                    }
                });

        try {
            ChannelFuture future = client.connect("127.0.0.1", 9999).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            worker.shutdownGracefully();
        }
    }


    private static class MessageHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.wrappedBuffer("hello world".getBytes(CharsetUtil.UTF_8)));
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
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
