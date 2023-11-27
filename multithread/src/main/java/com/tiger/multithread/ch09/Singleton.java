package com.tiger.multithread.ch09;

/**
 * @Author Zenghu
 * @Date 2022年04月18日 22:45
 * @Description
 * @Version: 1.0
 **/
public class Singleton {

    public static Singleton instance = new Singleton(); // 2

    private static int x = 0; // 1
    private static int y; // 1




    static {
        System.out.println("执行静态代码块");
        x++;
        y++;

    }

    private Singleton() {
        System.out.println("执行构造函数");
        x++;
        y++;
    }

    public static Singleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        // 类的加载过程：加载 -> 验证 -> 准备 -> 解析 -> 初始化 -> 使用 ->卸载
        // 准备阶段： 为类变量分配空间，并赋初始值
        // 初始化阶段：类变量赋值和执行静态代码块

        // 准备阶段： x = 0, y = 0, instance = null
        // 初始化阶段： x = 1, y = 1, instance = 对象地址

        // 1与2交换位置
        // 准备阶段： x = 0, y = 0, instance = null
        // 初始化阶段： 执行构造函数： x = 1, y = 1, 执行完构造函数后： x=0, y=1


        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton.x);
        System.out.println(singleton.y);
    }

}
