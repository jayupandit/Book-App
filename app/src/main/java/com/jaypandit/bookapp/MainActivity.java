package com.jaypandit.bookapp;

import static com.jaypandit.bookapp.R.id.fab_Google;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FloatingActionButton google;
    float v=0;
    String[] titles = new String[]{"Login","SignUp"};
    LoginAdapter loginAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        google = findViewById(fab_Google);


        loginAdapter = new LoginAdapter(this);
        viewPager.setAdapter(loginAdapter);

        new TabLayoutMediator(tabLayout,viewPager,((tab, position) -> tab.setText(titles[position]))).attach();

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EmailOtpVerification.class));
            }
        });

//         fb.setTranslationY(300);
//         google.setTranslationY(300);
//         twitter.setTranslationY(300);

//         fb.setAlpha(v);
//         google.setAlpha(v);
//         twitter.setAlpha(v);

           tabLayout.setAlpha(v);

//         fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
//         google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
//         twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
         tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();



    }

}