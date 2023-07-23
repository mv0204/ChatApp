package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.chatapp.models.UserModel;
import com.example.chatapp.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginUsernameScreen extends AppCompatActivity {
    Button nextUsernameButton;
    ProgressBar progressBar;
    EditText usernameInput;
    String phoneNo;
    UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username_screen);
        nextUsernameButton = findViewById(R.id.login_username_next);
        progressBar = findViewById(R.id.login_username_progress);
        usernameInput = findViewById(R.id.login_username_tv);
        phoneNo = getIntent().getStringExtra("phone");
        getUserName();

        nextUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setUserName();
            }
        });


    }

    private void setUserName() {
        setInProgress(true);
        String username = usernameInput.getText().toString();
        if (username.isEmpty() || username.length() < 3) {
            usernameInput.setError("Enter A Valid UserName");
            return;
        }

        if (userModel != null) {
            userModel.setUserName(username);
        } else {
            userModel = new UserModel(phoneNo, username, Timestamp.now(),FirebaseUtils.getUserId());
        }
        FirebaseUtils.getUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
            if(task.isSuccessful()){
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            }
        });


    }

    private void getUserName() {
        setInProgress(true);
        FirebaseUtils.getUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if (task.isSuccessful()) {
                    userModel = task.getResult().toObject(UserModel.class);
                    if (userModel != null) {
                        usernameInput.setText(userModel.getUserName());
                    }
                }
            }
        });
    }

    void setInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            nextUsernameButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            nextUsernameButton.setVisibility(View.VISIBLE);
        }
    }

}