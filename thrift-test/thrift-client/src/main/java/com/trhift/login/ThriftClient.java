package com.trhift.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThriftClient {

    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(6);

    public static void main(String[] args) {
        TSocket socket = new TSocket("127.0.0.1", 9000);
        TTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TCompactProtocol(transport);
        LoginService.Client client = new LoginService.Client(protocol);
        try {
            transport.open();
            Request request = new Request();
            request.setName("test");
            request.setPassword("123456");
            scheduledThreadPool.scheduleAtFixedRate(() -> {
                String result = null;
                try {
                    result = client.doAction(request);
                } catch (TException e) {
                    e.printStackTrace();
                }
                log.info("result:{}", result);
            }, 0, 100, TimeUnit.MILLISECONDS);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
