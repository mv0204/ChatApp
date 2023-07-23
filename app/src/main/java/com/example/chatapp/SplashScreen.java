package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.chatapp.utils.FirebaseUtils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseUtils.isLoggedIn())
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                else{
                    startActivity(new Intent(SplashScreen.this,LoginNoScreen.class));}
                finish();
            }
        },500);
    }
}