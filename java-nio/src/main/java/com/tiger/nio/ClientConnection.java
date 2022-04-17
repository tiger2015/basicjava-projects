package com.tiger.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ClientConnection {
    private Selector selector;
    private SocketChannel channel;

    public ClientConnection(Selector selector, SocketChannel channel) {
        this.selector = selector;
        this.channel = channel;
    }

    public void read() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int len = 0;
            while ( (len = channel.read(buffer)) > 0) {
                byte[] msg = new byte[len];
                buffer.flip();
                buffer.get(msg);
                log.info("receive from {}:{}", channel.getRemoteAddress(), new String(msg, StandardCharsets.UTF_8));
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void write(String msg) {
        try {
            channel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void close() {
        if (this.channel != null) {
            try {
                this.channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
