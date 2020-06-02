package com.tiger.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tiger.rabbitmq.common.MyMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Sender
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/6/2 14:56
 * @Version 1.0
 **/
@Slf4j
public class Sender {
    private String queueName = "test";
    private String host;
    private Channel channel;

    public Sender(String host, String queueName) {
        this.host = host;
        this.queueName = queueName;
    }

    public void connect() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        log.info("connect success");
    }


    public void send(MyMessage myMessage) throws IOException {
        if (!Objects.isNull(channel)) {
            channel.basicPublish("", queueName, null, MyMessage.convertToByte(myMessage));
            log.info("send message:{}", myMessage);
        }else {
            log.warn("channel is not active");
        }
    }

    public void close() throws IOException, TimeoutException {
        if (channel != null)
            channel.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        Sender sender = new Sender("192.168.200.201", "test");
        sender.connect();
        schedulePool.scheduleAtFixedRate(() -> {
            MyMessage message = new MyMessage();
            message.setId(String.valueOf(System.currentTimeMillis() / 1000));
            message.setMessage("mesage-" + message.getId());
            try {
                sender.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }
}
