package com.example.dark_chat;

public class Message {
    String message, senderId;
    long Timestamp;

    public Message()
    {

    }

    public Message(String message, String senderId, long timestamp)
    {
        this.message = message;
        this.senderId = senderId;
        Timestamp = timestamp;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }
}
