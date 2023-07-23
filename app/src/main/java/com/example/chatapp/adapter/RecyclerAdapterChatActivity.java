package com.example.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ChatActivity;
import com.example.chatapp.R;
import com.example.chatapp.SearchActivity;
import com.example.chatapp.models.ChatMessageModel;
import com.example.chatapp.utils.AndroidUtils;
import com.example.chatapp.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecyclerAdapterChatActivity extends FirestoreRecyclerAdapter<ChatMessageModel,RecyclerAdapterChatActivity.ChatModelViewHolder>  {
    Context context;


    public RecyclerAdapterChatActivity(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options,Context context) {
        super(options);
        this.context=context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        if(model.getSenderId().equals(FirebaseUtils.getUserId())){
            holder.leftLL.setVisibility(View.GONE);
            holder.rightLL.setVisibility(View.VISIBLE);
            holder.rightTV.setText(model.getMessage());
        }else {
            holder.rightLL.setVisibility(View.GONE);
            holder.leftLL.setVisibility(View.VISIBLE);
            holder.leftTV.setText(model.getMessage());
        }
    
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row,parent,false);

        return new ChatModelViewHolder(view);
    }


    class ChatModelViewHolder extends RecyclerView.ViewHolder{
        TextView leftTV,rightTV;
        LinearLayout leftLL,rightLL;
        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);
           
            leftLL=itemView.findViewById(R.id.receiverLayout);
            rightLL=itemView.findViewById(R.id.senderLayout);
            leftTV=itemView.findViewById(R.id.receiverTV);
            rightTV=itemView.findViewById(R.id.senderTV);
            
        }
    }

}
