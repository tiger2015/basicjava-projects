package com.tiger.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ClientConnection {
    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer;

    public ClientConnection(Selector selector, SocketChannel channel) {
        this.selector = selector;
        this.channel = channel;
        buffer = ByteBuffer.allocate(1024);
    }

    public void read() {
        try {
            int len;
            while ((len = channel.read(buffer)) > 0) {
                buffer.flip();
                byte[] msg = new byte[len];
                buffer.get(msg);
                log.info("read：" + new String(msg, StandardCharsets.UTF_8));
                buffer.clear();
            }
            channel.register(selector, SelectionKey.OP_WRITE, this);
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void write() {
        try {
            channel.write(ByteBuffer.wrap("Hello\r\n".getBytes("UTF-8")));
            channel.register(selector, SelectionKey.OP_READ, this);
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
