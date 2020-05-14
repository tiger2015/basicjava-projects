package com.tiger.rpc.common;

import java.io.Serializable;

/**
 * @ClassName RpcResponse
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/12 20:22
 * @Version 1.0
 **/
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = 1894523995893089115L;
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "result=" + result +
                '}';
    }
}
