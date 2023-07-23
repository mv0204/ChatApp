package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.adapter.RecyclerAdapterSearchActivity;
import com.example.chatapp.models.UserModel;
import com.example.chatapp.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchActivity extends AppCompatActivity {
    EditText searchET;
    RecyclerAdapterSearchActivity adapter;
    ImageButton searchBtn, backBtn;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchET = findViewById(R.id.searchETSearchActivity);
        searchBtn = findViewById(R.id.searchBtnSearchActivity);
        backBtn = findViewById(R.id.backButtonSearchActivity);
        recyclerView = findViewById(R.id.recyclerViewSearchActivity);
        searchET.requestFocus();
        backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = searchET.getText().toString();
                    searchUser(s);
                    return true;
                }
                return false;
            }
        });

        searchBtn.setOnClickListener(view -> {
            String s = searchET.getText().toString();
            searchUser(s);

        });
    }

    private void searchUser(String s) {
        if (s.isEmpty() || s.length() < 3) {
            searchET.setError("Enter a valid Name ");
            searchET.setText("");
            searchBtn.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    searchET.setError(null);
                    searchBtn.setVisibility(View.VISIBLE);

                }
            }, 2000);
        }else {
            setupRecyclerView(s);

        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        searchET.requestFocus(0);
    }

    private void setupRecyclerView(String searchItem) {
        Query query = FirebaseUtils.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("userName", searchItem);
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class).build();
        adapter = new RecyclerAdapterSearchActivity(options, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}