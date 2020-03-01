package tiger.com.tiger.netty.common;

public enum MessageType {

    LOGIN(0, "login"),
    STATUS(1, "status"),
    LOGOUT(2, "logout"),
    USER_LIST(3, "list"),
    SESSION(4, "session"),
    NONE(-1, "none");
    public final int flag;
    public final String prefix;

    MessageType(int flag, String prefix) {
        this.flag = flag;
        this.prefix = prefix;
    }

    public static MessageType getMessageType(int flag) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.flag == flag) {
                return messageType;
            }
        }
        return NONE;
    }
}
