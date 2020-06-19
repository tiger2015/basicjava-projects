package com.tiger.rabbitmq.common;

public enum ExchangeType {

    DIRECT("direct"),
    FANOUT("fanout"),
    TOPIC("topic");

    public final String name;

    ExchangeType(String name) {
        this.name = name;
    }
}
