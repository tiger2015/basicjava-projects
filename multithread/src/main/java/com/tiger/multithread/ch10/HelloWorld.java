package com.tiger.multithread.ch10;

public class HelloWorld {

    static {
        System.out.println("load static code block");
    }

    public void sayHello(String name){
        System.out.println("hello " + name);
    }
}
