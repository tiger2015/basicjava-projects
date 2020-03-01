package com.tiger.multithread.edamodel;

public interface Message {
    Class<? extends Message> getType();
}
