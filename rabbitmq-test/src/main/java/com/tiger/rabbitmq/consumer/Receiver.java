package com.tiger.rabbitmq.consumer;

import com.rabbitmq.client.*;
import com.tiger.rabbitmq.common.ChannelFactory;
import com.tiger.rabbitmq.common.ExchangeType;
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
    private String flag;
    private String exchange;
    private ExchangeType type;
    private String route;
    private String queue;
    private Channel channel;

    public Receiver(String flag, String exchange, ExchangeType type, String route, String queue) {
        this.flag = flag;
        this.exchange = exchange;
        this.type = type;
        this.route = route;
        this.queue = queue;
    }

    public void connect() throws IOException, TimeoutException {
        channel = ChannelFactory.createChannel();
        channel.queueDeclare(queue, true, false, false, null);
        //channel.exchangeDeclare(exchange, type.name);
        channel.queueBind(queue, exchange, route);
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
            log.info("flag {} receive message:{}", flag, message);
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver receiver = new Receiver("consumer1", "exchange", ExchangeType.TOPIC, "test.hello", "test");
        receiver.connect();
        receiver.consume();

        Receiver receiver2 = new Receiver("consumer2", "exchange", ExchangeType.TOPIC, "test.#", "test1");
        receiver2.connect();
        receiver2.consume();

    }


}
