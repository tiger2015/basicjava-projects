package com.tiger.multithread.workerthread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.concurrent.ThreadLocalRandom.current;

public class Test {
    public static void main(String[] args) {
        final ProductionChannel channel = new ProductionChannel(5);
        AtomicInteger productionNo = new AtomicInteger();
        IntStream.range(1, 8).forEach(i -> new Thread(() -> {
            while (true) {
                try {
                    channel.offerProduction(new Production(productionNo.getAndIncrement()));
                    TimeUnit.SECONDS.sleep(current().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}
