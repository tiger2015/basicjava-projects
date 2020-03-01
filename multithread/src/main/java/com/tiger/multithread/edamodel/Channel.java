package com.tiger.multithread.edamodel;

public interface Channel<E extends Message> {
    void dispatch(E message);
}
