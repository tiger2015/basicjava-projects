package com.tiger.netty.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatedUser implements Serializable {
    private static final long serialVersionUID = 3728091763954429022L;
    private String account;
    private List<ChatRecord> chatRecords;

    public RelatedUser(String account) {
        this.account = account;
        this.chatRecords = new ArrayList<>();
    }

    public void addChatRecord(ChatRecord chatRecord) {
        if (this.chatRecords.size() > 15) {
            this.chatRecords.remove(0);
        }
        this.chatRecords.add(chatRecord);
    }

    public List<ChatRecord> getChatRecords() {
        return chatRecords;
    }

    @Override
    public String toString() {
        return account;
    }
}
