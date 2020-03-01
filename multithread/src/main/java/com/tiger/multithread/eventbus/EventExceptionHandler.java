package com.tiger.multithread.eventbus;

public interface EventExceptionHandler {

    void handle(Throwable cause, EventContext context);
}
