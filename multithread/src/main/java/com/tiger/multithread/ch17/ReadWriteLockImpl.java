package com.tiger.multithread.ch17;

/**
 * @Author Zenghu
 * @Date 2022年06月04日 16:53
 * @Description
 * @Version: 1.0
 **/
public class ReadWriteLockImpl implements ReadWriteLock {

    private final Object MUTEX = new Object();

    private int writingWriters = 0;

    private int waitingWriters = 0;

    private int readingReaders = 0;

    private boolean preferWriter;


    public ReadWriteLockImpl() {
        this(true);
    }

    public ReadWriteLockImpl(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    @Override
    public Lock readLock() {
        return new ReadLock(this);
    }

    @Override
    public Lock writeLock() {
        return new WriteLock(this);
    }


    public void incrementWritingWriters() {
        this.writingWriters++;
    }


    public void decrementWritingWriters() {
        this.writingWriters--;
    }


    public void incrementWaitingWriters() {
        this.waitingWriters++;
    }


    public void decrementWaitingWriters() {
        this.waitingWriters--;
    }


    public void incrementReadingReaders() {
        this.readingReaders++;
    }

    public void decrementReadingReaders() {
        this.readingReaders--;
    }

    @Override
    public int getWritingWriters() {
        return this.writingWriters;
    }

    @Override
    public int getWaitingWriters() {
        return this.waitingWriters;
    }

    @Override
    public int getReadingReaders() {
        return this.readingReaders;
    }

    public Object getMutex(){
        return this.MUTEX;
    }

    public boolean getPreferWriter() {
        return preferWriter;
    }

    void changePrefer(boolean preferWriter){
        this.preferWriter = preferWriter;
    }
}
