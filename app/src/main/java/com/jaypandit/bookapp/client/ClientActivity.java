package com.jaypandit.bookapp.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.client.fragment.HomeFragment;
import com.jaypandit.bookapp.client.fragment.SearchFragment;
import com.jaypandit.bookapp.client.fragment.SettingFragment;

public class ClientActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        getSupportActionBar().hide();

        frameLayout = findViewById(R.id.frame_layout);
        navigationView = findViewById(R.id.bottomNavigationView);

        replaceFragment(new HomeFragment());

        navigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.navi_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.navi_search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.navi_setting:
                    replaceFragment(new SettingFragment());
                    break;

            }
            return true;
        });



    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}