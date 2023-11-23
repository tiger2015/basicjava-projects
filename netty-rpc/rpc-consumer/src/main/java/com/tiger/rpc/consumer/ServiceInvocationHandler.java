package com.tiger.rpc.consumer;

import com.tiger.rpc.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class ServiceInvocationHandler implements InvocationHandler {
    private static Class serviceClass = UserService.class;
    private ServiceConsumer consumer;

    public ServiceInvocationHandler(ServiceConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       log.info("invoke method");
        return null;
    }
}
