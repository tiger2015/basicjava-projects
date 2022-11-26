package com.tiger.rpc.provider.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ServiceProvider
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/12 20:28
 * @Version 1.0
 **/
@Slf4j
public class ServiceProvider {
    private NioEventLoopGroup worker = new NioEventLoopGroup(8);
    private NioEventLoopGroup boss = new NioEventLoopGroup(4);
    private int port;

    public ServiceProvider(int port) {
        this.port = port;
    }

    public void start() {
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitHandler());
            ChannelFuture future = server.bind(port).sync();
            log.info("start service provider");
            // 该方法会阻塞当前线程执行
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("start service provider fail", e);
        }
    }


    private class ChannelInitHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(ServiceProvider.this.getClass().getClassLoader())));
            ch.pipeline().addLast(new RequestHandler());
            ch.pipeline().addLast(new ObjectEncoder());
        }
    }

    public static void main(String[] args) {
        ServiceProvider serviceProvider = new ServiceProvider(8000);
        serviceProvider.start();
    }

}
