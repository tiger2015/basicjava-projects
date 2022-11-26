package com.tiger.multithread.ch17;

/**
 * @Author Zenghu
 * @Date 2022年06月04日 16:50
 * @Description
 * @Version: 1.0
 **/
public interface ReadWriteLock {

    Lock readLock();

    Lock writeLock();

    int getWritingWriters();

    int getWaitingWriters();

    int getReadingReaders();

    static ReadWriteLock readWriteLock() {
        return new ReadWriteLockImpl();
    }

    static ReadWriteLock readWriteLock(boolean preferWriter) {
        return new ReadWriteLockImpl(preferWriter);
    }

}
