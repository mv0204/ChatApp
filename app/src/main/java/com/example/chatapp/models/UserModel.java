package com.example.chatapp.models;

import com.google.firebase.Timestamp;

public class UserModel {
    String phoneNo,userName,userId;
    Timestamp createdTimeStamp;

    public UserModel() {
    }

    public UserModel(String phoneNo, String userName, Timestamp createdTimeStamp,String userId) {
        this.phoneNo = phoneNo;
        this.userName = userName;
        this.userId=userId;
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
