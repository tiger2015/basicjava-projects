package tiger.com.tiger.netty.entity;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import tiger.com.tiger.netty.common.UserState;

@Slf4j
public class User {
    private String id; // 用户编号
    private UserState state = UserState.LOGOUT; // 用户状态
    private Channel channel; // 用户连接通道

    public User(String id, Channel channel) {
        this.id = id;
        this.channel = channel;
        this.state = UserState.LOGIN;
    }

    public boolean send(byte[] message) {
        if (channel != null && channel.isActive()) {
            this.channel.writeAndFlush(Unpooled.copiedBuffer(message)).addListener(future -> {
                if (!future.isSuccess()) {
                    log.info("user:{} send message fail", id);
                    close();
                }
            });
            return true;
        } else {
            close();
            return false;
        }
    }

    public boolean close() {
        if (channel != null) {
            channel.close();
        }
        log.info("user:{} logout", id);
        this.state = UserState.LOGOUT;
        return true;
    }

    public String getId() {
        return id;
    }

    public UserState getState() {
        return state;
    }
}
