package com.tiger.multithread.ch07;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * hook线程测试
 */
public class HookThreadTest {
    private static final String LOCK_FILE = "./lock";

    public static void main(String[] args) throws IOException {

        checkRunning();
        // 在主线程退出时会执行
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("the program receive kill signal");
            Paths.get(LOCK_FILE).toFile().delete();
        }));
        ThreadGroup group = new ThreadGroup("group01");
        // 所有线程的父线程都时main线程
        // 创建线程的线程作为父线程
       Thread thread =  new Thread(group, ()->{
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName()+":program is running ....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
       // 设置为守护线程，当主线程结束时，会结束
       //thread.setDaemon(true);
       thread.start();
    }
    // 判断文件是否存在，如果不存在，则创建文件
    private static void checkRunning() throws IOException {
        Path path = Paths.get(LOCK_FILE);
        if(path.toFile().exists()){
            throw new RuntimeException("program is already running!");
        }
        Files.createFile(path);
        System.out.println("create lock file");
    }
}
