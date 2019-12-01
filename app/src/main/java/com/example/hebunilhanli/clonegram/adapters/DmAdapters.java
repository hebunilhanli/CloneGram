package com.example.hebunilhanli.clonegram.adapters;


import java.util.Date;

public class DmAdapters {

    private String messageText;
    private String messageUser;
    private long messageTime;

    public DmAdapters(String messageText,String messageUser){

        this.messageText = messageText;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();



    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
