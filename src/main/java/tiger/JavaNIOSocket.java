package tiger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

public class JavaNIOSocket {
    private static int port = 9000;

    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.setOption(StandardSocketOptions.SO_RCVBUF, 1024 * 1024);
            Selector selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);

            new Thread(()->{
                Set<SelectionKey> keys = selector.selectedKeys();
                for(SelectionKey key: keys){
                    if(key.isReadable()){

                    }else if(key.isWritable()){

                    }else if(key.isConnectable()){

                    }
                }

            }).start();

            server.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
