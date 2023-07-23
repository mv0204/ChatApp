package com.example.chatapp.models;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel {
    String chatroomId,lastMessageSenderID,lastMessage;
    List<String> userIds;
    Timestamp lastMessageTimestamp;


    public ChatroomModel() {
    }

    public ChatroomModel(String chatroomId, String lastMessageSenderID, List<String> userIds, Timestamp lastMessageTimestamp) {
        this.chatroomId = chatroomId;
        this.lastMessageSenderID = lastMessageSenderID;
        this.userIds = userIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getLastMessageSenderID() {
        return lastMessageSenderID;
    }

    public void setLastMessageSenderID(String lastMessageSenderID) {
        this.lastMessageSenderID = lastMessageSenderID;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
