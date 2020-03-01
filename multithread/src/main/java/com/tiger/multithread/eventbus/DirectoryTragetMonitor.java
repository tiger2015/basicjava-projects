package com.tiger.multithread.eventbus;

import java.io.IOException;
import java.nio.file.*;

public class DirectoryTragetMonitor {
    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTragetMonitor(final EventBus eventBus, final String targetPath) {
        this(eventBus, targetPath, "");

    }


    public DirectoryTragetMonitor(final EventBus eventBus, final String targetPath, final String... morePath) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePath);
    }

    public void startMonitor() throws IOException {

        this.watchService = FileSystems.getDefault().newWatchService();

        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        this.start = true;
        while (start) {
            WatchKey watchKey = null;
            try {
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    Path child = DirectoryTragetMonitor.this.path.resolve(path);
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            } catch (InterruptedException e) {
                this.start = false;
            } finally {
                if (watchKey != null) {
                    watchKey.reset();
                }
            }
        }
    }


    public void stopMonitor() throws IOException {
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
    }


}
