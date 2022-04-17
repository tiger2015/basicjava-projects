package com.tiger.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class NIOServer {
    private int port;
    private ServerSocketChannel server = null;
    private Selector selector = null;

    public NIOServer(int port) {
        this.port = port;
    }

    public void init() {
        try {
            server = ServerSocketChannel.open();
            selector = Selector.open();
            // 设置为非阻塞
            server.configureBlocking(false);
            server.setOption(StandardSocketOptions.SO_RCVBUF, 1024 * 1024);
            server.bind(new InetSocketAddress(port));
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            log.error("init error", e);
        }
    }

    public void start() throws IOException {
        while (true) {

            int count = selector.select(3000);
            if (count <= 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                try {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        handleAcceptable(key);
                    }
                    if (key.isReadable()) {
                        handleReadable(key);
                    }
                } catch (Exception e) {
                    log.error("server occur error", e);
                } finally {
                    iterator.remove();
                }
            }
        }
    }

    private void handleAcceptable(SelectionKey key) throws IOException {
        try {
            SocketChannel socketChannel = server.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ, new ClientConnection(key.selector(), socketChannel));
            log.info("connect:{}", socketChannel.getRemoteAddress());
        } catch (IOException e) {
            key.channel().close();
            key.cancel();
        }
    }

    private void handleReadable(SelectionKey key) {
        ClientConnection connection = (ClientConnection) key.attachment();
        connection.read();
    }

    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer(6666);
        server.init();
        server.start();
    }
}
