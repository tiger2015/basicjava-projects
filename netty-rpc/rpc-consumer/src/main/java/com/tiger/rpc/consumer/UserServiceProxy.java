package com.tiger.rpc.consumer;

import com.google.protobuf.ByteString;
import com.tiger.rpc.common.KryoUtil;
import com.tiger.rpc.common.protos.RpcParam;
import com.tiger.rpc.common.protos.RpcRequest;
import com.tiger.rpc.common.protos.RpcResponse;
import com.tiger.rpc.service.UserService;

import java.lang.reflect.Proxy;

public class UserServiceProxy {
    private static Class serviceClass = UserService.class;
    private ServiceConsumer consumer;

    public UserServiceProxy(ServiceConsumer consumer) {
        this.consumer = consumer;
    }

    public UserService newUserServicePorxy() {

        return (UserService) Proxy.newProxyInstance(consumer.getClass().getClassLoader(),
                new Class[]{UserService.class}, (proxy, method, args) -> {
                    // RpcRequest request = new RpcRequest();
                    //  request.setClassName(serviceClass.getName());
                    // request.setMethodName(method.getName());
                    //  request.setTypes(method.getParameterTypes());
                    //  request.setParams(args);
                    //  consumer.send(request);
                    RpcRequest.Builder builder = RpcRequest.newBuilder();
                    builder.setClazz(serviceClass.getName())
                            .setMethod(method.getName());
                    for (Object arg : args) {
                        RpcParam.Builder param = RpcParam.newBuilder();
                        param.setType(arg.getClass().getName())
                                .setValue(ByteString.copyFrom(KryoUtil.serialize(arg)));
                        builder.addParams(param.build());
                    }
                    // 发送请求
                    RpcResponse response = consumer.send(builder.build());
                    if (response.getStatus() == 200) {
                        return KryoUtil.deserialize(response.getValue().toByteArray());
                    }
                    throw new RuntimeException("call service error");
                });
    }
}
