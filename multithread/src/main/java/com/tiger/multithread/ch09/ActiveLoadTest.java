package com.tiger.multithread.ch09;

public class ActiveLoadTest {

    public static void main(String[] args) {
       // System.out.println(Child.x); // 导致父类初始化
       // System.out.println(Child.y); // 导致父类和子类都初始化

       // Parent []parents = new Child[10]; // 数组不会导致类被初始化
      //  System.out.println(Child.LEN); // 静态常量不会导致类被初始化

        System.out.println(Child.RADOM); // 导致类被初始化
    }

}
