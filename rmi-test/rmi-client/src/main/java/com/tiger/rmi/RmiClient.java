package com.tiger.rmi;

import com.tiger.rmi.service.HelloService;
import com.tiger.rmi.service.UserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RmiClient {
    private static ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(4);
    private static HelloService helloService;
    private static UserService userService;

    private static void initService() {
        try {
            System.out.println("init service");
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9000);
            helloService = (HelloService) registry.lookup("hello");
            userService = (UserService) registry.lookup("user");
            // HelloService helloService = (HelloService) Naming.lookup("rmi://192.168.100.201:9000/hello");
            // UserService userService = (UserService) Naming.lookup("rmi://192.168.100.201:9000/user");
        } catch (NotBoundException e) {
            e.printStackTrace();
            System.out.println("未绑定");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("服务端错误");
        }
    }


    public static void main(String[] args) throws RemoteException {
        initService();
        /**
        User user = new User();
        user.setName("test");
        user.setAge(24);
        user.setSex('M');
        user.setJob("coder");
        userService.save(user);
       **/


        scheduleThreadPool.scheduleAtFixedRate(() -> {
            if (helloService == null || userService == null) {
                initService();
                return;
            }
            try {
                System.out.println(helloService.sayHello("test"));
                System.out.println(userService.get("test"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }, 0, 3, TimeUnit.SECONDS);

    }
}
