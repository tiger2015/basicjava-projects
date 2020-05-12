package com.tiger.rpc.provider.service;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import com.sun.xml.internal.bind.api.ClassResolver;
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

/**
 * @ClassName ServiceProvider
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/12 20:28
 * @Version 1.0
 **/
public class ServiceProvider {
    private NioEventLoopGroup worker = new NioEventLoopGroup(8);
    private NioEventLoopGroup boss = new NioEventLoopGroup(4);
    private int port;

    public void start() {
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitHandler());
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {

        }
    }


    private class ChannelInitHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(ServiceProvider.this.getClass().getClassLoader())));
            ch.pipeline().addLast(new ObjectEncoder());
            ch.pipeline().addLast(new RequestHandler());
        }
    }


}
