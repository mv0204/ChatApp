package com.example.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ChatActivity;
import com.example.chatapp.R;
import com.example.chatapp.models.ChatroomModel;
import com.example.chatapp.models.UserModel;
import com.example.chatapp.utils.AndroidUtils;
import com.example.chatapp.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerAdapterRecentChat extends FirestoreRecyclerAdapter<ChatroomModel,RecyclerAdapterRecentChat.ChatroomModelViewHolder>  {
    Context context;
    UserModel otherUserModel;

    public RecyclerAdapterRecentChat(@NonNull FirestoreRecyclerOptions<ChatroomModel> options,Context context) {
        super(options);
        this.context=context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ChatroomModelViewHolder holder, int position, @NonNull ChatroomModel model) {
     FirebaseUtils.getOtherUserFromChatroom(model.getUserIds())
             .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    otherUserModel =task.getResult().toObject(UserModel.class);
                    holder.userNameTV.setText(otherUserModel.getUserName());
                    holder.lastMessage.setText(model.getLastMessage());
                    holder.lastTimestamp.setText(FirebaseUtils.getStringFromTimestamp(model.getLastMessageTimestamp()));
                }
                 }
             });

        holder.itemView.setOnClickListener(v->{
            Intent intent =new Intent(context,ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            AndroidUtils.passUserModelAsIntent(intent,otherUserModel);
            context.startActivity(intent);

        });
    }

    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_view,parent,false);

        return new ChatroomModelViewHolder(view);
    }


    class ChatroomModelViewHolder extends RecyclerView.ViewHolder{
        TextView userNameTV,lastMessage,lastTimestamp;
        ImageView userImage;
        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV=itemView.findViewById(R.id.recentChatRowUserNameTV);
            lastMessage=itemView.findViewById(R.id.recentChatRowLastMsgTV);
            userImage=itemView.findViewById(R.id.profile_pic_view);
            lastTimestamp=itemView.findViewById(R.id.recentChatRowTimeTV);
        }
    }

}
