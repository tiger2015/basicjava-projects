package com.tiger.ssh;

import com.tiger.ssh.handler.ProtocolConfirmHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Zenghu
 * @Date 2022年11月25日 9:51
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class SSHClient {
    private static NioEventLoopGroup worker = new NioEventLoopGroup();

    private String host;
    private int port;

    public SSHClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect(){
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024*4));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ProtocolConfirmHandler());
                    }
                });

        bootstrap.connect(host, port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    log.info("connect success");
                }else {
                    log.warn("connect fail");
                }
            }
        });
    }









}
