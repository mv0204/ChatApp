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
import com.example.chatapp.SearchActivity;
import com.example.chatapp.models.UserModel;
import com.example.chatapp.utils.AndroidUtils;
import com.example.chatapp.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecyclerAdapterSearchActivity extends FirestoreRecyclerAdapter<UserModel,RecyclerAdapterSearchActivity.UserModelViewHolder>  {
Context context;


    public RecyclerAdapterSearchActivity(@NonNull FirestoreRecyclerOptions<UserModel> options,Context context) {
        super(options);
        this.context=context;
    }


    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull UserModel model) {
    holder.userNameTV.setText(model.getUserName());
    holder.phoneNoTV.setText(model.getPhoneNo());
    if(model.getUserId().equals(FirebaseUtils.getUserId())){
        holder.userNameTV.setText(model.getUserName()+" (ME)");
    }
    holder.itemView.setOnClickListener(v->{
        Intent intent =new Intent(context,ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        AndroidUtils.passUserModelAsIntent(intent,model);
        context.startActivity(intent);

    });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row,parent,false);

        return new UserModelViewHolder(view);
    }


    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView userNameTV,phoneNoTV;
        ImageView userImage;
        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV=itemView.findViewById(R.id.searchRowUserNameTV);
            phoneNoTV=itemView.findViewById(R.id.searchRowNoTV);
            userImage=itemView.findViewById(R.id.profile_pic_view);
        }
    }

}
