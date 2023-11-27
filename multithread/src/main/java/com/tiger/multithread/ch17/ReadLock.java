package com.tiger.multithread.ch17;

/**
 * @Author Zenghu
 * @Date 2022年06月04日 16:56
 * @Description
 * @Version: 1.0
 **/
public class ReadLock implements Lock {


    private final ReadWriteLockImpl readWriteLock;

    public ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMutex()) {
            while (readWriteLock.getWritingWriters() > 0
                    || (readWriteLock.getPreferWriter() && readWriteLock.getWaitingWriters() > 0)) {
                readWriteLock.getMutex().wait();
            }
            readWriteLock.incrementReadingReaders();
        }

    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMutex()){
            readWriteLock.decrementReadingReaders();
            readWriteLock.changePrefer(true);
            readWriteLock.getMutex().notifyAll();
        }
    }
}
