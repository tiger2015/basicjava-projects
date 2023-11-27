package com.tiger.multithread.ch17;

/**
 * @Author Zenghu
 * @Date 2022年06月04日 16:58
 * @Description
 * @Version: 1.0
 **/
public class WriteLock implements Lock {


    private final ReadWriteLockImpl readWriteLock;

    public WriteLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMutex()) {
            try{
                readWriteLock.incrementWaitingWriters();
                while (readWriteLock.getWritingWriters() > 0 ||
                        readWriteLock.getReadingReaders() > 0) {
                    readWriteLock.getMutex().wait();
                }
            }finally {
                this.readWriteLock.decrementWaitingWriters();
            }
            this.readWriteLock.incrementWritingWriters();
        }

    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMutex()){
            readWriteLock.decrementWritingWriters();
            readWriteLock.changePrefer(false);
            readWriteLock.getMutex().notifyAll();

        }

    }
}
