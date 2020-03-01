package com.tiger.multithread.ch10;

public class LoaderTest  extends StaticTest{

    static {
        System.out.println(LoaderTest.class.getName()+":初始化静态代码块");
    }

    public LoaderTest(){
        super();
        System.out.println(this.getClass().getName()+":初始化");
    }

    public static void main(String[] args) {
        LoaderTest loaderTest = new LoaderTest();
    }
}
