package com.thrift.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.concurrent.Executors;

@Slf4j
public class ThriftServer {
    public static void main(String[] args) {
        try {
            // Socket类型
            TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(9000);
            // processor
            LoginService.Processor processor = new LoginService.Processor(new LoginServiceImpl());

            // 服务器参数
            THsHaServer.Args threadPoolServerArgs = new THsHaServer.Args(serverSocket);
            threadPoolServerArgs.executorService(Executors.newFixedThreadPool(3));
            // 传输数据格式
            threadPoolServerArgs.protocolFactory(new TCompactProtocol.Factory());
            // 传输数据方式
            threadPoolServerArgs.transportFactory(new TFastFramedTransport.Factory());
            // 创建处理器处理RPC请求
            threadPoolServerArgs.processorFactory(new TProcessorFactory(processor));
            TServer server = new THsHaServer(threadPoolServerArgs);
            log.info("start service");
            // 阻塞方法
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
