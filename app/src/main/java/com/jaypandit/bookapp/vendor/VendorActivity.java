package com.jaypandit.bookapp.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jaypandit.bookapp.R;

public class VendorActivity extends AppCompatActivity {

    LinearLayout layoutBookVendor,layoutUniformVendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        layoutBookVendor = findViewById(R.id.layout_book_vendor);
        layoutUniformVendor = findViewById(R.id.layout_uniform_vendor);

        layoutBookVendor.setOnClickListener(view -> {

            Intent i = new Intent(this,BookVendorActivity.class);
            startActivity(i);
        });

        layoutUniformVendor.setOnClickListener(view -> {

            Intent i = new Intent(this,UniformVendorActivity.class);
            startActivity(i);
        });
    }
}