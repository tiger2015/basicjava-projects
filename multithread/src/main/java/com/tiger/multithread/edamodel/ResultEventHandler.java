package com.tiger.multithread.edamodel;

public class ResultEventHandler implements Channel<ResultEvent>{
    @Override
    public void dispatch(ResultEvent message) {
        System.out.println("the result is:"+message.getResult());
    }
}
