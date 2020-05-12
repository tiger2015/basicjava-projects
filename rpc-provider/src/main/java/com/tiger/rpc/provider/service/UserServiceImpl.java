package com.tiger.rpc.provider.service;

import com.tiger.rpc.service.UserService;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/11 20:15
 * @Version 1.0
 **/
public class UserServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        return "hello, " + name + "!";
    }
}
