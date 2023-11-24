package com.tiger.rpc.provider.service;

import com.tiger.rpc.common.protos.RpcRequest;
import com.tiger.rpc.service.EmployeeService;
import com.tiger.rpc.service.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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

    private UserService userService = new UserServiceImpl();
    private EmployeeService employeeService = new EmployeeServiceImpl();

    public ServiceProvider(int port) {
        this.port = port;
    }

    public void start() {
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
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
            //  ch.pipeline().addLast(new ObjectDecoder(1024 * 1024,
            //         ClassResolvers.weakCachingConcurrentResolver(ServiceProvider.this.getClass().getClassLoader())));
            // ch.pipeline().addLast(new RequestHandler());
            //  ch.pipeline().addLast(new ObjectEncoder());

            // 处理protobuf
            // in
            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
            // in
            ch.pipeline().addLast(new ProtobufDecoder(RpcRequest.getDefaultInstance()));
            // out
            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
            // out
            ch.pipeline().addLast(new ProtobufEncoder());

            ch.pipeline().addLast(new RpcRequestHandler(userService, employeeService));

        }
    }

    public static void main(String[] args) {
        ServiceProvider serviceProvider = new ServiceProvider(8000);
        serviceProvider.start();
    }

}
