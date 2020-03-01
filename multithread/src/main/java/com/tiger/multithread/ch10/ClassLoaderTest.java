package com.tiger.multithread.ch10;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Path path = Paths.get("D:\\IDEAProjects\\learning-projects\\multithread-learning\\target\\classes");
        MyClassLoader classLoader = new MyClassLoader(path);
        Class<?> aClass = classLoader.loadClass("com.tiger.multithread.ch10.HelloWorld");
        // 执行下面方法会加载静态代码块
        Object instance = aClass.newInstance();
        System.out.println(instance);
        // 获取方法
        Method method = aClass.getMethod("sayHello", String.class);
        method.invoke(instance, "test");
    }

}
