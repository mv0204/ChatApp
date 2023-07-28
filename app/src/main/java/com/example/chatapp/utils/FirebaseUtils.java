package com.example.chatapp.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.common.hash.HashCode;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.SimpleTimeZone;

public class FirebaseUtils {
    public static String getUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn() {
        if (getUserId() != null) {
            return true;
        }
        return false;
    }


    public static DocumentReference getUserDetails() {
        return FirebaseFirestore.getInstance().collection("users").document(getUserId());
    }

    public static CollectionReference allUserCollectionReference() {
        return FirebaseFirestore.getInstance().collection("users");
    }

    public static DocumentReference getChatRoomReference(String chatRoomID) {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatRoomID);
    }

    public static CollectionReference chatMessageCollectionReference(String chatRoomID) {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatRoomID).collection("chats");
    }

    public static String createChatRoomID(String userId1, String userId2) {
        if (userId1.hashCode() < userId2.hashCode()) {
            return userId1 + "_" + userId2;
        } else {
            return userId2 + "_" + userId1;
        }

    }

    public static CollectionReference allChatroomCollectionReference() {
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public static DocumentReference getOtherUserFromChatroom(List<String> list) {
        if (list.get(0).equals(FirebaseUtils.getUserId())) {
            return FirebaseFirestore.getInstance().collection("users").document(list.get(1));
        } else {
            return FirebaseFirestore.getInstance().collection("users").document(list.get(0));
        }
    }

    public static String getStringFromTimestamp(Timestamp timestamp) {
        return new SimpleDateFormat("HH:mm").format(timestamp.toDate());
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static StorageReference getCurrentUserProfilePicStorageReference() {
        return FirebaseStorage.getInstance().getReference().child("profilePic").child(FirebaseUtils.getUserId());
    }

    public static StorageReference getOtherUserProfilePicStorageReference(String otherUserId) {
        return FirebaseStorage.getInstance().getReference().child("profilePic").child(otherUserId);

    }


}
