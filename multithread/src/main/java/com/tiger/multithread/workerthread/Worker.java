package com.tiger.multithread.workerthread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Worker extends Thread {

    private final ProductionChannel channel;

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public Worker(String wokerName, ProductionChannel channel){
        super(wokerName);
        this.channel = channel;
    }

    @Override
    public void run() {

        while (true){
            try {
                Production production = channel.takeProduction();
                System.out.println(getName() +" process the " + production);
                production.create();
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
