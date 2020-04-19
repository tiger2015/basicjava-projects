package tiger.com.tiger.netty;


public interface Client {

    boolean connect(String ip, int port);

    void start();

    boolean close();

    boolean reconnect();

    boolean send(byte[] message);

    boolean login(String account, String password);

}
