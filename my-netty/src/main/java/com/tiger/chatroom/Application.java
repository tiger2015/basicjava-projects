package com.tiger.chatroom;

public class Application {

    public static void main(String[] args) {
        Client client = new NettyClient();
        client.start();
    }
}
