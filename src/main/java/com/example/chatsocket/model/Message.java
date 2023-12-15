package com.example.chatsocket.model;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Message {
    private int messageId;
    private int senderUser;
    private int receiverUser;
    private String content;
    private String timeStamp;

    public Message() {

    }

    public Message(int messageId, int senderUser, int receiverUser, String content, DateTimeFormatter timeStamp){

    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(int senderUser) {
        this.senderUser = senderUser;
    }

    public int getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(int receiverUser) {
        this.receiverUser = receiverUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
