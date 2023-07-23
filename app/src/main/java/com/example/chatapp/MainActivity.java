package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.chatapp.fragments.HomeFragment;
import com.example.chatapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ImageButton searchButton;
    BottomNavigationView bottomNavigationView;
    FrameLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton=findViewById(R.id.search_button);
        container=findViewById(R.id.fragmentContainer);
        bottomNavigationView=findViewById(R.id.bottomNV);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id == R.id.home) {
                    replaceFrag(new HomeFragment());
                } else if (id == R.id.profile) {
                    replaceFrag(new ProfileFragment());
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);
    }


    private void replaceFrag(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }
}