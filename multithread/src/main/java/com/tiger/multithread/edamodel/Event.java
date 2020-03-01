package com.tiger.multithread.edamodel;

public class Event implements Message {

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

}
