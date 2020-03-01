package tiger.com.tiger.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import tiger.com.tiger.netty.handler.ClientMessageHandler;
import tiger.com.tiger.netty.handler.CustomMessageDecoder;
import tiger.com.tiger.netty.handler.MessageFrameDecoder;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Server {
    private static ServerBootstrap bootstrap = new ServerBootstrap();
    static {
        System.setProperty("log4j.configurationFile","./config/");
    }

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(2);
        NioEventLoopGroup worker = new NioEventLoopGroup(16);
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .localAddress(9000)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new WriteTimeoutHandler(15, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new MessageFrameDecoder());
                        ch.pipeline().addLast(new CustomMessageDecoder());
                        ch.pipeline().addLast(new ClientMessageHandler());
                    }
                });
        try {
            log.info("start server .......");
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }

}
