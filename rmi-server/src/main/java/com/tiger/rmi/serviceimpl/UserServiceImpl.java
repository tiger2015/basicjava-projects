package com.tiger.rmi.serviceimpl;

import com.tiger.rmi.modle.User;
import com.tiger.rmi.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    public UserServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public User get(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(24);
        user.setSex('M');
        user.setJob("coder");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) {

    }
}
