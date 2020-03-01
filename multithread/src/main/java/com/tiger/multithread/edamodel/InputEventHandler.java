package com.tiger.multithread.edamodel;

public class InputEventHandler implements Channel<InputEvent> {
    private final EventDispatcher dispatcher;

    public InputEventHandler(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void dispatch(InputEvent message) {
        System.out.printf("X:%d,Y=%d\n", message.getX(), message.getY());
        int result = message.getX() + message.getY();
        dispatcher.dispatch(new ResultEvent(result));
    }
}
