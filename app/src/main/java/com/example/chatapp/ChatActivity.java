package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatapp.adapter.RecyclerAdapterChatActivity;
import com.example.chatapp.adapter.RecyclerAdapterSearchActivity;
import com.example.chatapp.models.ChatMessageModel;
import com.example.chatapp.models.ChatroomModel;
import com.example.chatapp.models.UserModel;
import com.example.chatapp.utils.AndroidUtils;
import com.example.chatapp.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {
    TextView userName;
    ImageButton sendBtn, backButton;
    RecyclerView recyclerView;
    EditText chatInput;
    UserModel otherUser;
    String chatroomID;
    ChatroomModel chatroomModel;
    RecyclerAdapterChatActivity adapterChatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userName = findViewById(R.id.chatUsernameAppBar);
        sendBtn = findViewById(R.id.sendBtnChatActivity);
        recyclerView = findViewById(R.id.recyclerViewChatActivity);
        backButton = findViewById(R.id.backButtonChatActivity);
        chatInput = findViewById(R.id.chatInputEditText);
        otherUser = AndroidUtils.getUserModelAsIntent(getIntent());
        userName.setText(otherUser.getUserName());

        chatroomID = FirebaseUtils.createChatRoomID(FirebaseUtils.getUserId(), otherUser.getUserId());


        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
        sendBtn.setOnClickListener(view -> {
            String msg =chatInput.getText().toString();
            if(msg!=null){
                sendMsg(msg);
            }
        });


        getOrCreateChatRoom(chatroomID);

        setupRecyclerView();
    }

        private void setupRecyclerView() {
            Query query = FirebaseUtils.chatMessageCollectionReference(chatroomID)
                    .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING );
            FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                    .setQuery(query, ChatMessageModel.class).build();
            adapterChatActivity = new RecyclerAdapterChatActivity(options, this);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            linearLayoutManager.setReverseLayout(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterChatActivity);
            adapterChatActivity.startListening();
            adapterChatActivity.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(0);
                        }
                    });
                }
            });

        }
    private void sendMsg(String msg) {
        if(msg.isEmpty()){
            return;
        }else {
        chatroomModel.setLastMessageSenderID(FirebaseUtils.getUserId());
        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessage(msg);
        FirebaseUtils.getChatRoomReference(chatroomID).set(chatroomModel);
        ChatMessageModel chatMessageModel=new ChatMessageModel(
                FirebaseUtils.getUserId(),msg,Timestamp.now()
        );

        FirebaseUtils.chatMessageCollectionReference(chatroomID).
                add(chatMessageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
            if(task.isSuccessful()){
                chatInput.setText("");
            }
            }
        });
    }}


    private void getOrCreateChatRoom(String chatroomID) {
        FirebaseUtils.getChatRoomReference(chatroomID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    chatroomModel = task.getResult().toObject(ChatroomModel.class);
                    if (chatroomModel == null) {
                        chatroomModel = new ChatroomModel(chatroomID, "",
                                Arrays.asList(FirebaseUtils.getUserId(),
                                        otherUser.getUserId()),
                                Timestamp.now());

                        FirebaseUtils.getChatRoomReference(chatroomID).set(chatroomModel);
                    }
                }
            }
        });
    }
}