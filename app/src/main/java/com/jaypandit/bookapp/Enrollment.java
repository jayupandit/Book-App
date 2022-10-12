package com.jaypandit.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Enrollment extends AppCompatActivity {

    private LinearLayout lineLayout,lineLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        getSupportActionBar().hide();

        lineLayout = findViewById(R.id.LinearLayout);
        lineLayout2 = findViewById(R.id.LinearLayout2);

        String email = getIntent().getStringExtra("mobile");
        Toast.makeText(this, email+"", Toast.LENGTH_SHORT).show();

        lineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Enrollment.this,SchoolEnrollmentActivity.class));
            }
        });

        lineLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Enrollment.this,CollegeEnrollmentActivity.class);
                i.putExtra("mobile",email);
                startActivity(i);
            }
        });
    }
}