package com.tiger.rmi;

import com.tiger.rmi.service.HelloService;
import com.tiger.rmi.service.UserService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RmiClient {
    private static ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(4);

    public static void main(String[] args) {

        scheduleThreadPool.scheduleAtFixedRate(() -> {
            try {
                HelloService helloService = (HelloService) Naming.lookup("rmi://192.168.100.201:9000/hello");
                System.out.println(helloService.sayHello("test"));

                UserService userService = (UserService) Naming.lookup("rmi://192.168.100.201:9000/user");
                System.out.println(userService.get("test"));

            } catch (NotBoundException e) {
                e.printStackTrace();
                System.out.println("未绑定");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("URL格式不正确");
            } catch (RemoteException e) {
                e.printStackTrace();
                System.out.println("服务端错误");
            }
        }, 0, 3, TimeUnit.SECONDS);


    }
}
