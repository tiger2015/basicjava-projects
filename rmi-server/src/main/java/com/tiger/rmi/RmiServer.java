package com.tiger.rmi;



import com.tiger.rmi.service.HelloService;
import com.tiger.rmi.service.UserService;
import com.tiger.rmi.serviceimpl.HelloServiceImpl;
import com.tiger.rmi.serviceimpl.UserServiceImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
    public static void main(String[] args) {
        try {
            HelloService helloService = new HelloServiceImpl();
            UserService userService = new UserServiceImpl();
            Registry registry = LocateRegistry.createRegistry(9000);
            registry.bind("hello", helloService);
            registry.bind("user", userService);
            // Naming.bind("rmi://127.0.0.1:9000/hello", helloService);
            // Naming.bind("rmi://127.0.0.1:9000/user",userService);
        } catch (RemoteException e) {
            e.printStackTrace();
            // } catch (MalformedURLException e) {
            //    e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

}
