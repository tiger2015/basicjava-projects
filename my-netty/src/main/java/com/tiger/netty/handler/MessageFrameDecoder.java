package com.tiger.netty.handler;

import com.tiger.netty.entity.MessageFrame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Arrays;
import java.util.List;

public class MessageFrameDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readableBytes();
        if (len < MessageFrame.minLength) { //一帧的数据长度至少为48byte
            return;
        }
        in.markReaderIndex();
        byte head = in.readByte();
        if (head == MessageFrame.head) {
            byte high = in.readByte();
            byte low = in.readByte();
            in.resetReaderIndex();
            int messageLength = (high << 8) + (low & 0xff); // 消息长度
            if (len < messageLength + MessageFrame.checkSzie) {
                return;
            }
            byte buffer[] = new byte[messageLength + MessageFrame.checkSzie];
            in.readBytes(buffer, 0, messageLength + MessageFrame.checkSzie);
            MessageFrame messageFrame = new MessageFrame();
            messageFrame.setLength(messageLength);
            int index = 3;
            messageFrame.setType(buffer[index]);
            index += 1;
            messageFrame.setFrom(Arrays.copyOfRange(buffer, index, index + MessageFrame.fromSize));
            index += MessageFrame.fromSize;
            messageFrame.setTo(Arrays.copyOfRange(buffer, index, index + MessageFrame.toSize));
            index += messageFrame.toSize;
            messageFrame.setBody(Arrays.copyOfRange(buffer, index, messageLength));
            messageFrame.setCheck(Arrays.copyOfRange(buffer, messageLength, messageLength + MessageFrame.checkSzie));
            out.add(messageFrame);
        } else {
            in.resetReaderIndex();
        }
    }
}
