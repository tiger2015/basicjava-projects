package com.tiger.multithread.ch10;

public class LoaderTest  extends StaticTest{

    static {
        System.out.println(LoaderTest.class.getName()+":初始化静态代码块");
    }

    public LoaderTest(){
        super();
        System.out.println(this.getClass().getName()+":初始化");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        CustomStringLoader customStringLoader = new CustomStringLoader("C:\\Users\\ZengHu\\Desktop");
        // 报错 找不到java.lang.Object
        Class<?> aClass = customStringLoader.loadClass("java2.lang.String");

        System.out.println(aClass);


        CustomStringLoader customStringLoader1 = new CustomStringLoader("C:\\Users\\ZengHu\\Desktop");

        Class<?> aClass1 = customStringLoader1.loadClass("java2.lang.String");

        System.out.println(aClass1);
        System.out.println(aClass == aClass1);

        // 不同实例的加载器加载同一个类得到不同的类实例

    }
}
