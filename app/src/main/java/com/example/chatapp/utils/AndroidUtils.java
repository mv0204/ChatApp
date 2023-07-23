package com.example.chatapp.utils;

import android.content.Context;
import android.content.Intent;

import com.example.chatapp.models.UserModel;

public class AndroidUtils {
    Context context;
    public static void passUserModelAsIntent(Intent intent, UserModel userModel){
        intent.putExtra("userName",userModel.getUserName());
        intent.putExtra("userId",userModel.getUserId());
        intent.putExtra("userPhone",userModel.getPhoneNo());
    }
    public static UserModel getUserModelAsIntent(Intent intent){
    UserModel userModel=new UserModel();
    userModel.setUserName(intent.getStringExtra("userName"));
    userModel.setUserId(intent.getStringExtra("userId"));
    userModel.setPhoneNo(intent.getStringExtra("userPhone"));
    return userModel;
    }

}
