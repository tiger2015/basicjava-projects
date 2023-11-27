package com.tiger.nio;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.CharsetUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author Zenghu
 * @Date 2021/7/13 21:36
 * @Description
 * @Version: 1.0
 **/
@Slf4j
public class NIOClient {
    private SocketChannel channel;
    private Selector selector;
    private String host = "127.0.0.1";
    private int port = -1;

    public NIOClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void connect() throws IOException {
        channel = SocketChannel.open();
        selector = Selector.open();
        channel.connect(new InetSocketAddress(host, port));
        while (!channel.finishConnect()) {

        }
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        log.info("connect success");
    }

    public void listenRead() throws IOException {
        while (true) {
            int select = selector.select(3000);
            if (select <= 0) continue;
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }
        }
    }


    private void handleRead(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        int len = 0;
        try {
            while ((len = channel.read(buffer)) > 0) {
                byte[] msg = new byte[len];
                buffer.flip();
                buffer.get(msg);
                log.info("receive:{}", new String(msg, StandardCharsets.UTF_8));
                buffer.clear();
            }
        } catch (Exception e) {
            log.error("read data error", e);
            key.cancel();
            channel.close();
        }
    }

    public void send(String msg) throws IOException {
        if (channel.isConnected()) {
            log.info("send:{}", msg);
            channel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        }
    }


    public static void main(String[] args) throws IOException {
        final NIOClient client = new NIOClient("127.0.0.1", 6666);
        client.connect();
        // 监听读写时间
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.listenRead();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while (true){
            String next = scanner.nextLine();
            client.send(next);
        }
    }
}
