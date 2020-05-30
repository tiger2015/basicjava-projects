package com.tiger.netty;

import com.tiger.netty.common.MessageType;
import com.tiger.netty.entity.CustomMessage;
import com.tiger.netty.entity.MessageFrame;
import com.tiger.netty.handler.CustomMessageDecoder;
import com.tiger.netty.handler.MessageFrameDecoder;
import com.tiger.netty.handler.ServerMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import com.tiger.netty.entity.MessageCallback;
import com.tiger.netty.ui.ClientMainFrame;
import com.tiger.netty.ui.LoginFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient implements Client {
    private Bootstrap bootstrap = new Bootstrap();
    private NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private String ip;
    private int port;
    private boolean loginSuccess;
    private String account;
    private String password;

    private List<String> userList = new ArrayList<>();

    private ClientMainFrame mainFrame;
    private LoginFrame loginFrame;

    public NettyClient() {
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new MessageFrameDecoder());
                        ch.pipeline().addLast(new CustomMessageDecoder());
                        ch.pipeline().addLast(new ServerMessageHandler(new ClientMessageCallback()));
                    }
                });

    }

    @Override
    public void start() {
        this.loginFrame = new LoginFrame(this);
        this.loginFrame.setVisible(true);
    }

    @Override
    public boolean connect(String ip, int port) {
        try {
            this.ip = ip;
            this.port = port;
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            channel = future.channel();
            log.info("connect success");
            return true;
        } catch (InterruptedException e) {
            log.info("connect fail", e);
            return false;
        }
    }

    @Override
    public boolean close() {
        if (channel != null) {
            channel.close();
            log.info("close connection");
        }
        return true;
    }

    @Override
    public boolean reconnect() {
        close();
        log.info("reconnection");
        return connect(this.ip, this.port);
    }

    @Override
    public boolean send(byte[] message) {
        if (channel == null) {
            throw new IllegalStateException("please connect before send");
        }
        if (channel.isActive()) {
            channel.writeAndFlush(Unpooled.copiedBuffer(message));
            return true;
        } else {
            login(this.account, this.password);
        }
        return false;
    }


    @Override
    public boolean login(String account, String password) {
        long connetTime = 0;
        this.account = account;
        this.password = password;
        for (; ; ) {
            try {
                if (channel == null || !channel.isActive()) {
                    this.connect("192.168.100.101", 9000);
                } else {
                    MessageFrame frame = new MessageFrame();
                    frame.setType((byte) MessageType.LOGIN.flag);
                    frame.setFrom(account.getBytes());
                    frame.setTo("server".getBytes());
                    frame.setBody(password.getBytes());
                    send(frame.toBytes());
                    long tryTime = 0;
                    for (; ; ) {
                        if (loginSuccess) break;
                        TimeUnit.MILLISECONDS.sleep(200);
                        tryTime += 200;
                        if (tryTime > 10000) break;
                    }
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(200);
                connetTime += 200;
                if (connetTime > 10000) {
                    break;
                }
            } catch (InterruptedException e) {
                log.error("login fail");
            }
        }
        for (; ; ) {
            if (loginSuccess) {
                this.mainFrame = new ClientMainFrame(account, this);
                this.mainFrame.setVisible(true);
                this.loginFrame.setVisible(false);
                this.mainFrame.updateUserList(userList);
                break;
            }
        }
        return true;
    }

    class ClientMessageCallback implements MessageCallback {

        @Override
        public void handle(CustomMessage customMessage) {  //消息回调
            log.info("user:{} receive:{}", account, customMessage.getBody());
            switch (customMessage.getType()) {
                case STATUS:
                    if (customMessage.getBody().equals("200")) {
                        loginSuccess = true;
                    }
                    break;
                case USER_LIST:
                    if (mainFrame != null) {
                        mainFrame.handleMessage(customMessage);
                    } else {
                        String[] splits = customMessage.getBody().split(",");
                        userList.clear();
                        for (String user : splits) {
                            userList.add(user);
                        }
                    }
                    break;
                case SESSION:
                    if (mainFrame != null) {
                        mainFrame.handleMessage(customMessage);
                    }else {
                        log.info("main frame is null");
                    }
                    break;
            }
        }
    }
}
