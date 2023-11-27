package com.tiger.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zenghu
 * @Date 2021/7/25 10:33
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class Client {

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup(2);

        Bootstrap client = new Bootstrap();

        client.group(group)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("1", new IdleStateHandler(30, 30, 20, TimeUnit.SECONDS));
                        ch.pipeline().addLast("2", new ProtobufDecoder(LoginEventProtobuf.UserEvent.getDefaultInstance())); // in
                        ch.pipeline().addLast("4", new ProtobufEncoder()); // out
                        ch.pipeline().addLast("3", new ClientHandler()); // in
                    }
                });

        client.connect("127.0.0.1", 9999)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            log.info("connect success");
                        }
                    }
                });
        log.info("client start connect");
    }


    @Slf4j
    static class ClientHandler extends SimpleChannelInboundHandler<LoginEventProtobuf.UserEvent> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            int rand = new Random().nextInt(2);
            LoginEventProtobuf.UserEvent userEvent = null;
            if (rand == 1) {
                LoginEventProtobuf.LoginEvent loginEvent = LoginEventProtobuf.LoginEvent.newBuilder()
                        .setName("test")
                        .setIp("127.0.0.1")
                        .setTime(System.currentTimeMillis())
                        .setSessionId(10000L).build();
                userEvent = LoginEventProtobuf.UserEvent.newBuilder().setLoginEvent(loginEvent)
                        .setType(LoginEventProtobuf.MessageType.LOGIN).build();

            } else {
                LoginEventProtobuf.LogoutEvent logoutEvent = LoginEventProtobuf.LogoutEvent.newBuilder()
                        .setName("test2")
                        .setIp("127.0.0.1")
                        .setTime(System.currentTimeMillis())
                        .setSessionId(10001L)
                        .build();
                userEvent = LoginEventProtobuf.UserEvent.newBuilder().setLogoutEvent(logoutEvent).setType(LoginEventProtobuf.MessageType.LOGOUT).build();
            }

            ctx.writeAndFlush(userEvent).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("send success");
                    } else {
                        log.warn("send fail", future.cause());
                    }
                }
            });
        }


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, LoginEventProtobuf.UserEvent msg) throws Exception {

            if (msg.getType() == LoginEventProtobuf.MessageType.LOGIN) {
                LoginEventProtobuf.LoginEvent loginEvent = msg.getLoginEvent();
                log.info("client receive login message:{}, {}, {}", loginEvent.getName(), loginEvent.getSessionId(), loginEvent.getTime());
            } else if (msg.getType() == LoginEventProtobuf.MessageType.LOGOUT) {
                LoginEventProtobuf.LogoutEvent logoutEvent = msg.getLogoutEvent();
                log.info("client receive logout message:{}, {}, {}", logoutEvent.getName(), logoutEvent.getSessionId(), logoutEvent.getTime());
            }
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
        }
    }

}
