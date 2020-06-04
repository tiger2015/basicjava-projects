package com.tiger.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tiger.rabbitmq.common.ChannelFactory;
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
    private String queue = "test";
    private String exchange;

    private Channel channel;

    public Sender(String queue, String exchange) {
        this.queue = queue;
        this.exchange = exchange;
    }

    public void connect() throws IOException, TimeoutException {
        channel = ChannelFactory.createChannel();
        // channel.queueDeclare(queue, true, false, false, null);
        channel.exchangeDeclare(exchange, "fanout");
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                log.info("deliver tag:{}", deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                log.info("deliver tag:{}", deliveryTag);
            }
        });
        log.info("connect success");
    }


    public void send(MyMessage myMessage) throws IOException {
        if (!Objects.isNull(channel)) {
            channel.basicPublish(exchange, "", null, MyMessage.convertToByte(myMessage));
            log.info("send message:{}", myMessage);
        } else {
            log.warn("channel is not active");
        }
    }

    public void close() throws IOException, TimeoutException {
        if (channel != null)
            channel.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        Sender sender = new Sender("test","exchange");
        sender.connect();

        /*
        for (int i = 0; i < 100000; i++) {
            MyMessage message = new MyMessage();
            message.setId(i + "");
            message.setMessage("mesage-" + message.getId());
            sender.send(message);
        }
        */

        schedulePool.scheduleAtFixedRate(() -> {
            MyMessage message = new MyMessage();
            message.setId(String.valueOf(System.currentTimeMillis() / 1000));
            message.setMessage("message-" + message.getId());
            try {
                sender.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

    }
}
