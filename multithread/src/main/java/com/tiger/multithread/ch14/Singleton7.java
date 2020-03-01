package com.tiger.multithread.ch14;

public enum  Singleton7 {

    INSTANCE;

    private byte[] data =new byte[1024];

    Singleton7(){

    }


    public static void method(){

    }


    public static Singleton7 getInstance(){
        return INSTANCE;
    }


}
