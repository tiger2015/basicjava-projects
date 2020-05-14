package com.tiger.rpc.consumer;

import com.tiger.rpc.common.RpcRequest;
import com.tiger.rpc.service.UserService;

import java.lang.reflect.Proxy;

public class UserServiceProxy {
    private static Class serviceClass = UserService.class;
    private ServiceConsumer consumer;

    public UserServiceProxy(ServiceConsumer consumer) {
        this.consumer = consumer;
    }

    public UserService newUserServicePorxy(){

        return (UserService) Proxy.newProxyInstance(consumer.getClass().getClassLoader(), new Class[]{UserService.class}, (proxy, method, args) -> {
            RpcRequest request = new RpcRequest();
            request.setClassName(serviceClass.getName());
            request.setMethodName(method.getName());
            request.setTypes(method.getParameterTypes());
            request.setParams(args);
            consumer.send(request);
            return null;
        });
    }
}
