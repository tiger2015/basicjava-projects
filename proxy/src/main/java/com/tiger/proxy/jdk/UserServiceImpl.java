package com.tiger.proxy.jdk;

public class UserServiceImpl implements UserService {
    @Override
    public void say(String message) {
        System.out.println("=====>hello " + message);
    }
}
