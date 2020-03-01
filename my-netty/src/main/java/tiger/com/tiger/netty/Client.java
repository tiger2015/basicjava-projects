package tiger.com.tiger.netty;


import tiger.com.tiger.netty.entity.MessageCallback;

import java.util.List;

public interface Client {

    boolean connect(String ip, int port);

    boolean close();

    boolean reconnect();

    boolean send(byte[] message);

    boolean isLoginSuccess();

    void setDisplayMessageCallback(MessageCallback callback);

    List<String> getUserList();

}
