package com.tiger.rpc.provider.service;

import com.google.protobuf.ByteString;
import com.tiger.rpc.common.KryoUtil;
import com.tiger.rpc.common.protos.RpcParam;
import com.tiger.rpc.common.protos.RpcRequest;
import com.tiger.rpc.common.protos.RpcResponse;
import com.tiger.rpc.service.EmployeeService;
import com.tiger.rpc.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: RpcRequestHandler
 * @Author: Zeng.h
 * @Date: 2023/11/24
 * @Description:
 * @Version: 1.0
 **/
@Slf4j
public class RpcRequestHandler extends ChannelInboundHandlerAdapter {

    private final UserService userService;
    private final Set<Class<?>> userServiceInterfaces = new HashSet<>();
    private final EmployeeService employeeService;
    private final Set<Class<?>> employeeServiceInterfaces = new HashSet<>();

    public RpcRequestHandler(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
        Class<?>[] interfaces = userService.getClass().getInterfaces();
        for (Class<?> cls : interfaces) {
            userServiceInterfaces.add(cls);
        }
        Class<?>[] interfaces1 = employeeService.getClass().getInterfaces();
        for (Class<?> cls : interfaces1) {
            employeeServiceInterfaces.add(cls);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        log.info("class:{}", request.getClazz());
        log.info("method:{}", request.getMethod());
        List<RpcParam> paramsList = request.getParamsList();
        RpcResponse.Builder builder = RpcResponse.newBuilder();
        try {
            Class<?> aClass = Class.forName(request.getClazz());
            Object targetObject = null;
            if (userServiceInterfaces.contains(aClass)) {
                targetObject = userService;
            } else if (employeeServiceInterfaces.contains(aClass)) {
                targetObject = employeeService;
            }
            if (targetObject != null) {
                Class<?>[] parameterTypes = new Class[request.getParamsCount()];
                Object[] args = new Object[request.getParamsCount()];
                for (int i = 0; i < paramsList.size(); i++) {
                    RpcParam params = request.getParams(i);
                    args[i] = KryoUtil.deserialize(params.getValue().toByteArray());
                    String type = params.getType();
                    if ("byte".equals(type)) {
                        parameterTypes[i] = byte.class;
                    } else if ("char".equals(type)) {
                        parameterTypes[i] = char.class;
                    } else if ("short".equals(type)) {
                        parameterTypes[i] = short.class;
                    } else if ("int".equals(type)) {
                        parameterTypes[i] = int.class;
                    } else if ("long".equals(type)) {
                        parameterTypes[i] = long.class;
                    } else if ("float".equals(type)) {
                        parameterTypes[i] = float.class;
                    } else if ("double".equals(type)) {
                        parameterTypes[i] = double.class;
                    } else if ("bool".equals(type)) {
                        parameterTypes[i] = boolean.class;
                    } else {
                        parameterTypes[i] = Class.forName(params.getType());
                    }
                    log.info("param[{}]:{}:{}", i, args[i], parameterTypes[i]);
                }
                Method method = aClass.getMethod(request.getMethod(), parameterTypes);
                Object result = method.invoke(targetObject, args);
                if (result != null) {
                    builder.setClazz(result.getClass().getName());
                    builder.setValue(ByteString.copyFrom(KryoUtil.serialize(result)));
                }
            }
            builder.setStatus(200);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            builder.setStatus(400);
        }
        ctx.channel().writeAndFlush(builder.build());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }
}
