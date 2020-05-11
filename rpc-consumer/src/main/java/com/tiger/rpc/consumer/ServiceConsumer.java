package com.tiger.rpc.consumer;

import com.tiger.rpc.common.RpcRequest;
import com.tiger.rpc.service.UserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName ServiceConsumer
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/11 20:20
 * @Version 1.0
 **/
public class ServiceConsumer {
     private static NioEventLoopGroup worker = new NioEventLoopGroup(4);
     private String ip;
     private int port;


     public void connect(){
         Bootstrap bootstrap = new Bootstrap();
         bootstrap.group(worker);
         bootstrap.channel(NioSocketChannel.class);
         bootstrap.handler(null)
                 .option(ChannelOption.TCP_NODELAY, true)
                 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                 .option(ChannelOption.SO_KEEPALIVE, true);
     }


     public void close(){

     }


     public void send(RpcRequest rpcRequest){

     }


    public static void main(String[] args) {









    }

}
