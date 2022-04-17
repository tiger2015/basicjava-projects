package com.tiger.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author Zenghu
 * @Date 2021/7/25 10:12
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class Server {


    public static void main(String[] args) {

        EventLoopGroup worker = new NioEventLoopGroup(4);
        EventLoopGroup boss = new NioEventLoopGroup(2);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 16)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        ch.pipeline().addLast(new IdleStateHandler(30, 30, 20, TimeUnit.SECONDS));
                        // in
                        ch.pipeline().addLast(new ProtobufDecoder(LoginEventProtobuf.UserEvent.getDefaultInstance()));
                        // out
                        ch.pipeline().addLast(new ProtobufEncoder());
                        // in
                        ch.pipeline().addLast(new ServerHandler());
                    }
                }).childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture future = bootstrap.bind(9999).sync();
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

}

@Slf4j
class ServerHandler extends SimpleChannelInboundHandler<LoginEventProtobuf.UserEvent> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("new connection");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginEventProtobuf.UserEvent msg) throws Exception {
        switch (msg.getType()) {
            case LOGIN:
                LoginEventProtobuf.LoginEvent loginEvent = msg.getLoginEvent();
                log.info("receive login message:{}, {}, {}", loginEvent.getName(), loginEvent.getSessionId(), loginEvent.getTime());
                break;
            case LOGOUT:
                LoginEventProtobuf.LogoutEvent logoutEvent = msg.getLogoutEvent();
                log.info("receive logout message:{}, {}, {}", logoutEvent.getName(), logoutEvent.getSessionId(), logoutEvent.getTime());
                break;
        }
        ctx.writeAndFlush(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
