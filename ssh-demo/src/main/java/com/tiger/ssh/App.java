package com.tiger.ssh;

/**
 * @Author Zenghu
 * @Date 2022年11月25日 11:02
 * @Description
 * @Version: 1.0
 **/
public class App {
    public static void main(String[] args) {

        SSHClient client = new SSHClient("192.168.100.6", 22);
        client.connect();


    }
}
