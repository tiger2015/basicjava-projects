package com.tiger.multithread;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/11/21 15:17
 * @Description:
 * @Version: 1.0
 **/
public class PrintTest2 {
    private static Object lock = new Object();


    public static void main(String[] args)  {
        new Thread(new PrintThread((char) 1, false)).start();
        new Thread(new PrintThread('A', true)).start();


    }


    static class PrintThread implements Runnable {
        private char ch;
        private boolean printChar;

        public PrintThread(char ch, boolean printChar) {
            this.ch = ch;
            this.printChar = printChar;
        }

        @Override
        public void run() {
            for (int i = 0; i < 26; i++) {
                synchronized (lock) {
                    if (printChar) {
                        System.out.print(ch++);
                    } else {
                        System.out.print((int) ch++);
                    }
                    lock.notifyAll();
                    if(i < 25){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
