package com.tiger.rabbitmq.consumer;

import com.rabbitmq.client.*;
import com.tiger.rabbitmq.common.ChannelFactory;
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
    private String queue;
    private String exchange;
    private Channel channel;


    public Receiver(String queue, String exchange) {
        this.queue = queue;
        this.exchange = exchange;
    }

    public void connect() throws IOException, TimeoutException {
        channel = ChannelFactory.createChannel();
        channel.queueDeclare(queue, false, false, true, null);
        channel.exchangeDeclare(exchange, "fanout");
        // queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exchange, "");
    }

    public void consume() throws IOException {
        if (!Objects.isNull(channel) && channel.isOpen()) {
            channel.basicConsume(queue, false, new MessageCallBack("user"));
        }
    }

    public void close() throws IOException, TimeoutException {
        if (!Objects.isNull(channel)) {
            channel.close();
        }
    }


    private class MessageCallBack implements Consumer {
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
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver receiver = new Receiver("test1", "exchange");
        receiver.connect();


        Receiver receiver2 = new Receiver("test2", "exchange");
        receiver2.connect();

        receiver.consume();
        receiver2.consume();

    }


}
