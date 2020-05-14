package com.tiger.rpc.consumer;

import com.tiger.rpc.common.RpcRequest;
import com.tiger.rpc.service.UserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ServiceConsumer
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/11 20:20
 * @Version 1.0
 **/
@Slf4j
public class ServiceConsumer {
    private static NioEventLoopGroup worker = new NioEventLoopGroup(4);
    private String ip;
    private int port;
    private Channel channel;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public ServiceConsumer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChnannelInitHandler())
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            future.addListener(future1 -> {
                if (!future1.isSuccess()) {
                    log.warn("connect fail, start reconnect");
                    channel.eventLoop().schedule(() -> connect(), 1, TimeUnit.SECONDS);
                }
            });
            channel = future.channel();
        } catch (Exception e) {
            log.error("connect fail", e);
        }
    }


    public void close() {
        if (!Objects.isNull(channel)) {
            channel.close();
        }
    }

    public void channelInactive() {
        countDownLatch = new CountDownLatch(1);
    }

    public void channelActive() {
        countDownLatch.countDown();
    }

    public void send(RpcRequest rpcRequest) throws InterruptedException {
        countDownLatch.await();
        if (!Objects.isNull(channel) && channel.isActive()) {
            channel.writeAndFlush(rpcRequest).addListener(future -> {
                if (future.isSuccess()) {
                    log.info("send request success");
                }
            });
        }
    }


    private class ChnannelInitHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(ServiceConsumer.class.getClassLoader())));
            ch.pipeline().addLast(new ObjectEncoder());
            ch.pipeline().addLast(new ResponseHandler(ServiceConsumer.this));
        }
    }


    public static void main(String[] args) {
        ServiceConsumer consumer = new ServiceConsumer("127.0.0.1", 8000);
        consumer.connect();
        UserServiceProxy proxy = new UserServiceProxy(consumer);
        UserService userService = proxy.newUserServicePorxy();
        userService.hello("ttttt");
    }

}
