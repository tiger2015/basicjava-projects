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
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
