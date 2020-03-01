package com.tiger.multithread.ch05.concurrent;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class BooleanLockTest {

    private final BooleanLock lock = new BooleanLock();

    public void synMethod() {
        try {
            lock.lock();
            int randomInt = current().nextInt(10);
            System.out.println(currentThread() + " get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    public static void main(String[] args) throws InterruptedException {

        currentThread().setName("test");
        System.out.println(currentThread());


        BooleanLockTest test = new BooleanLockTest();

        // ==================
        // IntStream.range(0,10).mapToObj(i->new Thread(test::synMethod)).forEach(Thread::start);


        // ===========
        // ====中断测试
        new Thread(test::synMethod, "T1").start();

        TimeUnit.MILLISECONDS.sleep(2);

        Thread t2 = new Thread(test::synMethod, "T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);
        t2.interrupt();

    }
}
