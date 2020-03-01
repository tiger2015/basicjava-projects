package com.tiger.multithread.eventbus;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FileChangeListener {

    @Subscribe
    public void onChange(FileChangeEvent event) {
        System.out.printf("%s-%s\n", event.getPath(), event.getKind());
    }

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        final EventBus bus = new AsyncEventBus(executor);
        bus.register(new FileChangeListener());
        DirectoryTragetMonitor monitor = new DirectoryTragetMonitor(bus, "E:\\monitor");
        monitor.startMonitor();
    }

}
