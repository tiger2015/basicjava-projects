package com.tiger.netty.handler;

import com.tiger.netty.common.MessageType;
import com.tiger.netty.entity.MessageFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import com.tiger.netty.entity.CustomMessage;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CustomMessageDecoder extends MessageToMessageDecoder<MessageFrame> {

    @Override
    protected void decode(ChannelHandlerContext ctx, MessageFrame msg, List<Object> out) throws Exception {
        CustomMessage customMessage = new CustomMessage();
        customMessage.setType(MessageType.getMessageType(msg.getType()));
        customMessage.setFrom(new String(msg.getFrom(), UTF_8).trim());
        customMessage.setTo(new String(msg.getTo(), UTF_8).trim());
        customMessage.setBody(new String(msg.getBody(), UTF_8));
        out.add(customMessage);
    }
}
