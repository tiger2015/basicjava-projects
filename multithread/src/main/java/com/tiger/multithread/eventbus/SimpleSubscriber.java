package com.tiger.multithread.eventbus;

public class SimpleSubscriber {

    @Subscribe
    public void method1(String message) {
        System.out.println("====SimpleSubscriber====method1====" + message);
    }

    @Subscribe(topic = "test")
    public void method2(String message){
        System.out.println("=====SimpleSubscriber=====method2===="+message);

    }


    public static void main(String[] args) {

        Bus bus = new EventBus("Test");
        bus.register(new SimpleSubscriber());
        bus.post("hello");
        bus.post("hello","test");

    }
}
