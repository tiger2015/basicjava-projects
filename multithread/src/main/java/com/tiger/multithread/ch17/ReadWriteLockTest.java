package com.tiger.multithread.ch17;

/**
 * @Author Zenghu
 * @Date 2022年06月04日 17:53
 * @Description
 * @Version: 1.0
 **/
public class ReadWriteLockTest {


    private static final String text = "21234242dsfsdfsdfsdfsdfjgjghj";

    public static void main(String[] args) {

        final ShareData shareData = new ShareData(50);
        // 写线程
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int index = 0; index < text.length(); index++) {
                        char c = text.charAt(index);
                        try {
                            shareData.write(c);
                            System.out.println(Thread.currentThread() + " write " + c);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        // 读线程
        for (int i = 0; i < 10; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println(Thread.currentThread()+" read " + new String(shareData.read()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }

}
