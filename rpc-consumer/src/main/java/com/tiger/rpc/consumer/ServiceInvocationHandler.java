package com.tiger.rpc.consumer;

import com.tiger.rpc.common.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceInvocationHandler implements InvocationHandler {
    private ServiceConsumer consumer;

    public ServiceInvocationHandler(ServiceConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setTypes(method.getParameterTypes());
        consumer.send(request);
        return null;
    }
}
