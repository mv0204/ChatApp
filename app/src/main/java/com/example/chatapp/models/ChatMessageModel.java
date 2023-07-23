package com.example.chatapp.models;

import com.google.firebase.Timestamp;

public class ChatMessageModel {
    String senderId,message;
    Timestamp lastMessageTimestamp;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String senderId, String message, Timestamp lastMessageTimestamp) {
        this.senderId = senderId;
        this.message = message;
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }
}
