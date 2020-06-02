package tiger;

import java.util.LinkedList;

public class VolatileTest {
    private static volatile User user = new User();


    public static class WriteThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    user.setAge(10 + (int) ((Math.random() * 10) % 2 + 1));
                    user.setName("test");
                    System.out.println("write:"+user.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static class ReadThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("read:" + user.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {



    }


}
