package tiger.com.tiger.netty.entity;

import tiger.com.tiger.netty.common.MessageType;

import java.io.Serializable;

public class CustomMessage implements Serializable {
    private static final long serialVersionUID = -840349445262721227L;
    private MessageType type;
    private String from;
    private String to;
    private String body;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
