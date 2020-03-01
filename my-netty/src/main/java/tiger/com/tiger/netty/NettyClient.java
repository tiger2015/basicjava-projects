package tiger.com.tiger.netty;

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
import tiger.com.tiger.netty.entity.CustomMessage;
import tiger.com.tiger.netty.entity.MessageCallback;
import tiger.com.tiger.netty.handler.CustomMessageDecoder;
import tiger.com.tiger.netty.handler.MessageFrameDecoder;
import tiger.com.tiger.netty.handler.ServerMessageHandler;

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


    private MessageCallback displayMessageCallback;
    private List<String> userList = new ArrayList<>();

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
        }
        return false;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    @Override
    public void setDisplayMessageCallback(MessageCallback callback) {
        displayMessageCallback = callback;
    }

    @Override
    public List<String> getUserList() {
        return this.userList;
    }

    class ClientMessageCallback implements MessageCallback {

        @Override
        public void handle(CustomMessage customMessage) {  //消息回调
            switch (customMessage.getType()) {
                case STATUS:
                    if (customMessage.getBody().equals("200")) {
                        loginSuccess = true;
                    }
                    break;
                case USER_LIST:
                    if (displayMessageCallback != null) {
                        displayMessageCallback.handle(customMessage);
                    } else {
                        String[] splits = customMessage.getBody().split(",");
                        userList.clear();
                        for (String user : splits) {
                            userList.add(user);
                        }
                    }
                    break;
                case SESSION:
                    displayMessageCallback.handle(customMessage);
                    break;
            }
        }
    }
}
