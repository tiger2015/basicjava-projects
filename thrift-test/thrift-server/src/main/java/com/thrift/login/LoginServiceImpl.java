package com.thrift.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginServiceImpl implements LoginService.Iface {
    @Override
    public String doAction(Request request) throws RequestException, TException {
        log.info("user:{}, password:{}", request.name, request.password);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return request.name + ":" + request.password;
    }
}
