package com.tiger.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tiger.rabbitmq.common.ChannelFactory;
import com.tiger.rabbitmq.common.ExchangeType;
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
    private String exchange;
    private String route;
    private ExchangeType type;
    private String queue = "test";
    private Channel channel;

    public Sender(String exchange, String route, ExchangeType type, String queue) {
        this.exchange = exchange;
        this.route = route;
        this.type = type;
        this.queue = queue;
    }

    public void connect() throws IOException, TimeoutException {
        channel = ChannelFactory.createChannel();
        // 队列交给消费者声明
        // channel.queueDeclare(queue, false, false, true, null);
        channel.exchangeDeclare(exchange, type.name);
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
            channel.basicPublish(exchange, route, null, MyMessage.convertToByte(myMessage));
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
        schedulePool.scheduleAtFixedRate(new SendTask("msg1", "exchange", "test.hello", ExchangeType.TOPIC, ""), 0, 1000, TimeUnit.MILLISECONDS);
        schedulePool.scheduleAtFixedRate(new SendTask("msg2", "exchange","test.hallo.", ExchangeType.TOPIC, ""), 0, 1000, TimeUnit.MILLISECONDS);
    }

    private static class SendTask implements Runnable {
        private Sender sender;
        private String name;

        public SendTask(String name, String exchange, String route, ExchangeType type, String queue) {
            this.name = name;
            this.sender = new Sender(exchange, route, type, queue);
            try {
                sender.connect();
            } catch (Exception e) {
                try {
                    sender.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (TimeoutException e1) {
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            MyMessage message = new MyMessage();
            message.setId(String.valueOf(System.currentTimeMillis() / 1000));
            message.setMessage(name + "-" + message.getId());
            try {
                this.sender.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
