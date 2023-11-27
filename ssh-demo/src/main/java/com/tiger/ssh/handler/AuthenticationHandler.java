package com.tiger.ssh.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * @Author Zenghu
 * @Date 2022年11月25日 11:17
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class AuthenticationHandler extends ChannelInboundHandlerAdapter {



     FileOutputStream outputStream;


    public AuthenticationHandler() {
        try {
            File file = new File("receive.bin");
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        int len = buf.readableBytes();
        byte[] reads = new byte[len];
        buf.readBytes(reads);
        log.info("receive:{}", Arrays.toString(reads));
        outputStream.write(reads);
        outputStream.flush();

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
