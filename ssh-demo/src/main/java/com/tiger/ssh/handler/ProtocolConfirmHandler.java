package com.tiger.ssh.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @Author Zenghu
 * @Date 2022年11月25日 10:57
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class ProtocolConfirmHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("receive:{}", msg);
        if(msg.startsWith("SSH-")){
            ByteBuf byteBuf = Unpooled.wrappedBuffer("SSH-2.0-OpenSSH_7.2p2\r\n".getBytes(Charset.defaultCharset()));
            ctx.write(byteBuf);
            ctx.flush();

            ctx.pipeline().remove(LineBasedFrameDecoder.class);
            ctx.pipeline().remove(StringDecoder.class);
            ctx.pipeline().remove(this);
            ctx.pipeline().addLast(new AuthenticationHandler());
        }
    }
}
