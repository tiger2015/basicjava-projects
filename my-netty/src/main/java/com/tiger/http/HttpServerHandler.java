package com.tiger.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Zenghu
 * @Date 2021/7/17 17:02
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            log.info("request url:{}", request.uri());
            log.info("hash code:{}", ctx.channel().hashCode());
            if (request.uri().equals("/favicon.ico")) return;

            ByteBuf content = Unpooled.wrappedBuffer("hello world".getBytes(CharsetUtil.UTF_8));

            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, content);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().add(HttpHeaderNames.CONNECTION, "keep-alive");  //
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("connection close");
        ctx.close();

    }
}
