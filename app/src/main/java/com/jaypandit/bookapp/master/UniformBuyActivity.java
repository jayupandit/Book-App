package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class UniformBuyActivity extends AppCompatActivity {

    ListView uniformView, otherUniView;

    EditText searchView;

    ArrayList<UniformSell> uniformList, otherUniList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_buy);

        searchView = findViewById(R.id.search_uni_buy_view);
        uniformView = findViewById(R.id.uniform_buy_list_view);
        otherUniView = findViewById(R.id.uniform_other_buy_list_view);

        getSupportActionBar().setTitle("Uniform Buy");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

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
        ArrayList<UniformSell> uniBuyList, otherBuyList;

        uniBuyList = new ArrayList<>();
        otherBuyList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uniBuyList.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    UniformSell book = schoolDataSnap.getValue(UniformSell.class);
                    if (book.getSetName().toLowerCase().contains(toString.toLowerCase()) || book.getBoard().toLowerCase().contains(toString.toLowerCase())
                            || book.getuClass().toLowerCase().contains(toString.toLowerCase())) {
                        uniBuyList.add(book);
                    }
                }

                UniformBuyAdapter adapter = new UniformBuyAdapter(UniformBuyActivity.this,uniBuyList);
                uniformView.setAdapter(adapter);
                adapter.filterList(uniBuyList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child("UniformSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otherBuyList.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    UniformSell book = schoolDataSnap.getValue(UniformSell.class);
                    if (book.getSetName().toLowerCase().contains(toString.toLowerCase()) || book.getBoard().toLowerCase().contains(toString.toLowerCase())
                            || book.getuClass().toLowerCase().contains(toString.toLowerCase())) {
                        otherBuyList.add(book);
                    }
                }

                UniformBuyAdapter adapter = new UniformBuyAdapter(UniformBuyActivity.this,otherBuyList);
                otherUniView.setAdapter(adapter);
                adapter.filterList(otherBuyList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}