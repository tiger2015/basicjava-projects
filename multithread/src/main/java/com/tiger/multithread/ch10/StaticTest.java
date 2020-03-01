package com.tiger.multithread.ch10;

public class StaticTest {
    static {
        System.out.println(StaticTest.class.getName() + ":初始化静态代码块");
    }
    public StaticTest(){
        System.out.println(this.getClass().getName()+":子类初始化");
        System.out.println(this.getClass().getSuperclass().getName()+":初始化");
    }

}
