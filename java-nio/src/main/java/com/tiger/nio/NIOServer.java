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

    public boolean init() {
        try {
            server = ServerSocketChannel.open();
            // 设置为非阻塞
            server.configureBlocking(false);
            server.setOption(StandardSocketOptions.SO_RCVBUF, 1024 * 1024);
            server.bind(new InetSocketAddress(port));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            return true;
        } catch (IOException e) {
            log.info("init error", e);
        }
        return false;
    }

    public void start() {
        while (true) {
            try {
                int count = selector.select(3000);
                if (count == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        handelAcceptable(key);
                    } else if (key.isWritable()) {
                        handleWritable(key);
                    } else if (key.isReadable()) {
                        handleReadable(key);
                    }
                    iterator.remove();
                }
            } catch (Exception e) {
                log.info("server occur error", e);
            }
        }
    }

    private void handelAcceptable(SelectionKey key) {
        try {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = channel.accept();
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ, new ClientConnection(key.selector(),
                    socketChannel));
            log.info("connect:{}:{}", socketChannel.socket().getInetAddress(),socketChannel.socket().getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReadable(SelectionKey key) {
        ClientConnection connection = (ClientConnection) key.attachment();
        connection.read();
    }

    private void handleWritable(SelectionKey key) {
        ClientConnection connection = (ClientConnection) key.attachment();
        if (connection == null) {
            log.info("connection close");
        } else {
            connection.write();
        }
    }


    public static void main(String[] args) {
        NIOServer server = new NIOServer(9009);
        if (server.init()) {
            server.start();
        }
    }
}
