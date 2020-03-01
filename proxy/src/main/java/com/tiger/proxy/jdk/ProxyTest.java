package com.tiger.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理:
 * 只能代理接口，目标类实现此接口，
 * 代理类需要实现InvocationHandler接口，注意需要目标类
 */

public class ProxyTest {
    public static void main(String[] args) {
        // 目标类
        UserService userService = new UserServiceImpl();
        // 代理类，传入目标类的接口
        UserService proxyInstance = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), new UserServiceInvocationHandler(userService));
        proxyInstance.say("test");
    }
}
