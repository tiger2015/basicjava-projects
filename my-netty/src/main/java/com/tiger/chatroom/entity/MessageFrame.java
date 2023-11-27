package com.tiger.chatroom.entity;

// 一帧数据的内容

import com.tiger.chatroom.util.CrcUtil;

import java.util.Arrays;

public class MessageFrame {
    public static final int headSize = 1; // 消息头 1byte
    public static final int lengthSize = 2; // 消息长度 2byte
    public static final int typeSzie = 1; // 消息类型 1byte
    public static final int fromSize = 20;//发送者 20byte
    public static final int toSize = 20; // 接收者 20byte
    public static final int checkSzie = 3; // 校验位 3byte
    public static final byte space = 0x20;

    public static final int minLength = 47;
    public static final byte head = 0x50;//消息头部  1byte

    private int length; // 消息长度 2byte 不包含校验位
    private byte type; // 消息类型 1byte
    private byte[] from; // 发送者 20byte
    private byte[] to; // 接收者 20byte
    private byte[] body; // 消息内容 不定长度
    private byte[] check; // 校验位 3byte

    public void setLength(int length) {
        this.length = length;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setFrom(byte[] from) {
        this.from = new byte[fromSize];
        Arrays.fill(this.from, space);
        System.arraycopy(from, 0, this.from, 0, from.length);
        if (from.length < fromSize) {
        }
    }

    public void setTo(byte[] to) {
        this.to = new byte[toSize];
        Arrays.fill(this.to, space);
        System.arraycopy(to, 0, this.to, 0, to.length);
    }

    public void setBody(byte[] body) {
        this.body = Arrays.copyOf(body, body.length);
    }

    public void setCheck(byte[] check) {
        this.check = Arrays.copyOf(check, check.length);
    }

    public int getLength() {
        return length;
    }

    public byte getType() {
        return type;
    }

    public byte[] getFrom() {
        return from;
    }

    public byte[] getTo() {
        return to;
    }

    public byte[] getBody() {
        return body;
    }

    public byte[] getCheck() {
        return check;
    }

    public byte[] toBytes() {
        byte[] sendBytes = new byte[4096];
        int index = 0;
        sendBytes[index] = head;
        index += 3;
        sendBytes[index] = type;
        index += 1;
        System.arraycopy(from, 0, sendBytes, index, fromSize);
        index += fromSize;
        System.arraycopy(to, 0, sendBytes, index, toSize);
        index += toSize;
        System.arraycopy(body, 0, sendBytes, index, body.length);
        index += body.length;
        // 生成校验位
        byte[] check = CrcUtil.generateCrc24q(sendBytes, index);
        sendBytes[index] = check[0];
        sendBytes[index + 1] = check[1];
        sendBytes[index + 2] = check[2];
        index += 3;
        // 设置数据长度
        sendBytes[1] = (byte) (index - 3 >> 8);
        sendBytes[2] = (byte) ((index - 3) & 0xff);
        return Arrays.copyOfRange(sendBytes, 0, index);
    }
}
