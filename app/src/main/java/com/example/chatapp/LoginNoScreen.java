package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginNoScreen extends AppCompatActivity {
    EditText phoneNo;
    Button getOTP;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_no_screen);
        phoneNo=findViewById(R.id.login_no_tv);
        getOTP=findViewById(R.id.login_no_get_otp);
        progressBar=findViewById(R.id.login_no_progress);
        progressBar.setVisibility(View.GONE);

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no=phoneNo.getText().toString().trim();
                if(no.length()!=10){
                    phoneNo.setError("Enter A Valid Phone No");
                }else{
                Intent i=new Intent(LoginNoScreen.this,LoginOtpScreen.class);
                i.putExtra("phone",no);
                startActivity(i);
            }}
        });
    }
}