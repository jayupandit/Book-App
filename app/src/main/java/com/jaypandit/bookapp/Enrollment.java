package com.jaypandit.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.book.BookEnrollmentActivity;
import com.jaypandit.bookapp.college.CollegeEnrollmentActivity;
import com.jaypandit.bookapp.school.SchoolEnrollmentActivity;
import com.jaypandit.bookapp.uniform.UniformActivity;
import com.jaypandit.bookapp.vendor.VendorActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Enrollment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout lineLayout,lineLayout2,linearLayout3,linearLayout4,linearLayout5;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar toolbar;
    String userId,phone;
    CircleImageView imageView;

    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        database = FirebaseDatabase.getInstance().getReference().child("User");


        //hooks
        lineLayout = findViewById(R.id.LinearLayout);
        lineLayout2 = findViewById(R.id.LinearLayout2);
        linearLayout3 = findViewById(R.id.LinearLayout3);
        linearLayout4 = findViewById(R.id.LinearLayout4);
        linearLayout5 = findViewById(R.id.LinearLayout5);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        userId = getIntent().getStringExtra("user");

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        phone = preferences.getString("mobile",null);

        //Hide or show items

        //--Navigation Drawer menu--
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        View header = navigationView.getHeaderView(0);
        imageView = (CircleImageView) header.findViewById(R.id.circle_img);


        lineLayout.setOnClickListener(view -> {

                Toast.makeText(Enrollment.this, phone+"", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Enrollment.this, SchoolEnrollmentActivity.class);
                startActivity(i);
        });

        lineLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Enrollment.this, CollegeEnrollmentActivity.class);
                i.putExtra("userId",userId);
                startActivity(i);
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Enrollment.this, BookEnrollmentActivity.class);
                startActivity(i);
            }
        });

        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Enrollment.this, UniformActivity.class);
                startActivity(i);
            }
        });

        linearLayout5.setOnClickListener(view -> {

            Intent i = new Intent(Enrollment.this, VendorActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserinfo();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final SharedPreferences preferences =getSharedPreferences("Data", Context.MODE_PRIVATE);
        switch (item.getItemId()){
            case R.id.nav_logout:
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(this,MainActivity.class));
                break;

            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rate:
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_my_profile:
                startActivity(new Intent(Enrollment.this,EditProfile.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUserinfo() {

        database.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    if (snapshot.hasChild("image")) {

                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(imageView);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}