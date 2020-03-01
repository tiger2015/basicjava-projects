package com.tiger.multithread.workerthread;

public abstract class InstructionBook {
    public final void create() {
        firstProcess();
        secondProcess();
    }


    protected abstract void firstProcess();

    protected abstract void secondProcess();


}
