package com.tiger.http;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author Zenghu
 * @Date 2021/7/17 16:53
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class HttpServer {


    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();

        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpServerCodec());
                        ch.pipeline().addLast(new HttpServerHandler());

                    }
                });


        try {
            // 同步等待操作
            ChannelFuture future = server.bind(3306);

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        log.info("start server success");
                    }else {
                        log.warn("start server fail");
                        log.error("exception", future.cause());
                        worker.shutdownGracefully();
                        boss.shutdownGracefully();
                    }
                }
            });

            // 阻塞主进程
            //future.awaitUninterruptibly();

//            if (future.isSuccess()){
//                log.info("start server success");
//            } else {
//                log.info("start server fail");
//                worker.shutdownGracefully();
//                boss.shutdownGracefully();
//            }


            /**
            // 当操作完成后会执行finally代码块中内容
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {

                    } else {
                        log.info("start server fail");
                    }
                }
            });
            // 让main线程进入阻塞状态
            future.channel().closeFuture().sync();
             **/
        } catch (Exception e) {
            log.error("start occur exception", e);
           // worker.shutdownGracefully();
           // boss.shutdownGracefully();
        } finally {
           // worker.shutdownGracefully();
            //boss.shutdownGracefully();
        }
    }


}
