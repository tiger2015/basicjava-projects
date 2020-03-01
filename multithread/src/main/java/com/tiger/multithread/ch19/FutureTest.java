package com.tiger.multithread.ch19;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {
    public static void main(String[] args) {
        FutureTask<String> service = new FutureTask<>(() -> "hello");
        new Thread(service).start();
        try {
            System.out.println(service.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }






    }
}
