package com.tiger.nio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

public class FileChannelTest {
    private static final int BUFFER_SIZE = 1024 * 1024;

    public static void readByFileChannel(String file) {
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            fis = new FileInputStream(file);
            channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
            while (channel.read(buffer) > 0) {
                buffer.flip();
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void readByInputStream(String file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (fis.read(buffer) > 0) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void readByBufferedInputStream(String file) {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[BUFFER_SIZE];
            while (bis.read(buffer) > 0) ;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        String file = "G:\\迅雷下载\\阳光电影www.ygdy8.com.星球大战9：天行者崛起.BD.1080p.国英双语双字.mkv";
        long start = System.currentTimeMillis();
        readByInputStream(file); // 43740ms  2203ms  1766ms
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        TimeUnit.SECONDS.sleep(30);

        start = System.currentTimeMillis();
        readByBufferedInputStream(file); // 1530ms  1844ms  1563ms
        end = System.currentTimeMillis();
        System.out.println(end - start);
        TimeUnit.SECONDS.sleep(30);
        start = System.currentTimeMillis();
        readByFileChannel(file); // 425ms  469ms  437ms
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
