package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginOtpScreen extends AppCompatActivity {
    Button getOtp;
    EditText otpInput;
    long timeOutTime=30L;
    ProgressBar progressBar;
    TextView resend;
    FirebaseAuth mAuth;
    String verificationCode;
    String phoneNo;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp_screen);
        phoneNo=("+91")+getIntent().getStringExtra("phone");
        getOtp =findViewById(R.id.login_otp_next);
        otpInput =findViewById(R.id.login_otp_tv);
        progressBar =findViewById(R.id.login_otp_progress);
        resend =findViewById(R.id.login_otp_resend);
        mAuth=FirebaseAuth.getInstance();
        sendOtp(phoneNo,false);

        getOtp.setOnClickListener(view -> {
            String otpFetched=otpInput.getText().toString();
            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCode,otpFetched);
            signIn(credential);
        });
        resend.setOnClickListener(view -> {
            sendOtp(phoneNo,true);
        });
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        setInProgress(true);
        //code to sign in and move to next activity
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(LoginOtpScreen.this,LoginUsernameScreen.class);
                    intent.putExtra("phone",phoneNo);
                    startActivity(intent);
                    setInProgress(false);
                }else {
                    Toast.makeText(LoginOtpScreen.this, "Otp Verification Failed", Toast.LENGTH_SHORT).show();
                    setInProgress(false);
                }
            }
        });

    }

    void sendOtp(String phone,boolean isResend){
        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(LoginOtpScreen.this, "Otp Verification Failed", Toast.LENGTH_SHORT).show();
                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                resendingToken=forceResendingToken;
                                verificationCode=s;
                                Toast.makeText(LoginOtpScreen.this, "Otp Sent successfully", Toast.LENGTH_SHORT).show();
                                setInProgress(false);

                            }
                        });// OnVerificationStateChangedCallbacks
    if(isResend){
        PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
    }else {
        PhoneAuthProvider.verifyPhoneNumber(builder.build());

    }

    }

    private void startResendTimer() {
        resend.setEnabled(false);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeOutTime--;
                runOnUiThread(()->{
                    resend.setText("resend OTP in "+timeOutTime+" sec");
                });

                if(timeOutTime<=0) {
                    timeOutTime = 30L;
                    timer.cancel();
                    runOnUiThread(() -> {resend.setEnabled(true);
                        resend.setText("resend OTP ");});
                }
            }
        },0,1000);
    }

    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            getOtp.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            getOtp.setVisibility(View.VISIBLE);
        }
    }

}