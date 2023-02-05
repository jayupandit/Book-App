package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.vendor.BookVendor;
import com.jaypandit.bookapp.vendor.UniformVendor;
import com.jaypandit.bookapp.vendor.UniformVendorAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class NewUniformActivity extends AppCompatActivity {

    ListView listView;
    TextView searchView;

    ArrayList<UniformVendor> vendorArrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_uniform);

        listView = findViewById(R.id.listViewNewBook);
        searchView = findViewById(R.id.search_buy_view);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


    }

    private void filter(String toString) {

        vendorArrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformVendor");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vendorArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UniformVendor uniformVendor = dataSnapshot.getValue(UniformVendor.class);
                    if(uniformVendor.getFullName().toLowerCase().contains(toString.toLowerCase(Locale.ROOT)) || uniformVendor.getShopCity().toLowerCase().contains(toString.toLowerCase(Locale.ROOT))
                            || uniformVendor.getShopAddress().toLowerCase().contains(toString.toLowerCase(Locale.ROOT)) || uniformVendor.getShopName().toLowerCase().contains(toString.toLowerCase(Locale.ROOT))) {
                        vendorArrayList.add(uniformVendor);
                    }
                }

                UniformVendorAdapter adapter = new UniformVendorAdapter(NewUniformActivity.this,vendorArrayList);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}