package com.healthmonitor.pojo;

public class Message {

    private int senderId;
    private int recieverId;
    private String content;
    private long timestamp;

    public Message() {
    }

    public Message(int senderId, int recieverId, String content, long timestamp) {
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.content = content;
        this.timestamp = timestamp;
    }
    
    public int getSenderId() {
        return this.senderId;
    }
    
    public void setSenderId(int sender) {
        this.senderId = sender;
    }
    
    public int getRecieverId() {
        return this.recieverId;
    }
    
    public void setRecieverId(int reciever) {
        this.recieverId = reciever;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
