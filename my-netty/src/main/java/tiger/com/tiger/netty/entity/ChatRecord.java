package tiger.com.tiger.netty.entity;

import java.io.Serializable;

/**
 * 用户的一条聊天记录
 */
public class ChatRecord implements Serializable {
    private static final long serialVersionUID = -890568155460281242L;
    public final String account; // 用户
    public final String time; // 时间
    public final String message; // 消息

    public ChatRecord(String account, String time, String message) {
        this.account = account;
        this.time = time;
        this.message = message;
    }

    @Override
    public String toString() {
        return "======="+time + "======\r\n" + account + ": " + message + "\r\n";
    }
}
