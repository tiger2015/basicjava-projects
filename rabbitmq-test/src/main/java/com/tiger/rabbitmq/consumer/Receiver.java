package com.tiger.rabbitmq.consumer;

import com.rabbitmq.client.*;
import com.tiger.rabbitmq.common.MyMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Receiver
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/6/2 15:39
 * @Version 1.0
 **/
@Slf4j
public class Receiver {
    private String queueName;
    private String host;
    private Channel channel;

    public Receiver(String queueName, String host) {
        this.queueName = queueName;
        this.host = host;
    }

    public void connect() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
    }

    public void consume(Consumer callback) throws IOException {
        if (!Objects.isNull(channel) && channel.isOpen()) {
            channel.basicConsume(queueName, callback);
        }
    }

    public void close() throws IOException, TimeoutException {
        if (!Objects.isNull(channel)) {
            channel.close();
        }
    }


    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver receiver = new Receiver("test", "192.168.200.201");
        receiver.connect();


        Receiver receiver2 = new Receiver("test", "192.168.200.201");
        receiver2.connect();

        receiver.consume(new MessageCallBack("user1"));
        receiver2.consume(new MessageCallBack("user2"));

    }


    private static class MessageCallBack implements Consumer {
        private String receiver;

        public MessageCallBack(String receiver) {
            this.receiver = receiver;
        }

        @Override
        public void handleConsumeOk(String consumerTag) {

        }

        @Override
        public void handleCancelOk(String consumerTag) {

        }

        @Override
        public void handleCancel(String consumerTag) throws IOException {

        }

        @Override
        public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

        }

        @Override
        public void handleRecoverOk(String consumerTag) {

        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            MyMessage message = MyMessage.convertToObject(body);
            log.info("{} receive message:{}", receiver, message);
        }
    }

}
