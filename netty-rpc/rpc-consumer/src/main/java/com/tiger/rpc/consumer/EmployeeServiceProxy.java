package com.tiger.rpc.consumer;

import com.google.protobuf.ByteString;
import com.tiger.rpc.common.KryoUtil;
import com.tiger.rpc.common.protos.RpcParam;
import com.tiger.rpc.common.protos.RpcRequest;
import com.tiger.rpc.common.protos.RpcResponse;
import com.tiger.rpc.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName: EmployeeServiceProxy
 * @Author: Zeng.h
 * @Date: 2023/11/24
 * @Description:
 * @Version: 1.0
 **/
@Slf4j
public class EmployeeServiceProxy {
    private static Class serviceClass = EmployeeService.class;
    private ServiceConsumer consumer;

    public EmployeeServiceProxy(ServiceConsumer consumer) {
        this.consumer = consumer;
    }

    public EmployeeService newEmployeeServiceProxy() {
        return (EmployeeService) Proxy.newProxyInstance(consumer.getClass().getClassLoader(), new Class[]{serviceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest.Builder builder = RpcRequest.newBuilder();
                        builder.setClazz(serviceClass.getName())
                                .setMethod(method.getName());
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        for (int i = 0; i < parameterTypes.length; i++) {
                            RpcParam.Builder param = RpcParam.newBuilder();
                            log.info("arg:{}", args[i].getClass().getName());
                            param.setType(parameterTypes[i].getName())
                                    .setValue(ByteString.copyFrom(KryoUtil.serialize(args[i])));
                            builder.addParams(param.build());
                        }
                        // 发送请求
                        RpcResponse response = consumer.send(builder.build());
                        if (response.getStatus() == 200) {
                            return KryoUtil.deserialize(response.getValue().toByteArray());
                        }
                        throw new RuntimeException("call service error");
                    }
                });

    }


}
