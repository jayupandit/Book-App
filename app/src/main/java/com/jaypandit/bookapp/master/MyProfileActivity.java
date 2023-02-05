package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class MyProfileActivity extends AppCompatActivity {

    TextView textView;

    DatabaseReference database;

    ArrayList<Address> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();

        textView = findViewById(R.id.textView10);

        arrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Address");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Address address = dataSnapshot.getValue(Address.class);
                    arrayList.add(address);
                }
                textView.setText(arrayList.get(0).getFullName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}