package com.tiger.rmi.service;

import com.tiger.rmi.modle.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {
    User get(String name) throws RemoteException;
    void save(User user) throws RemoteException;
}
