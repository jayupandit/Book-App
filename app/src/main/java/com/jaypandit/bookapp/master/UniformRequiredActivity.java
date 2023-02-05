package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class UniformRequiredActivity extends AppCompatActivity {

    private ListView uniListView,otherUniListView;

    ArrayList<UniformSell> uniformList,otherUniformList;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_required);

        uniListView = findViewById(R.id.uniReqLstView);
        otherUniListView = findViewById(R.id.otherUniReqListView);

        getSupportActionBar().setTitle("Uniform Required");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

    }



    @Override
    protected void onResume() {
        super.onResume();

        uniformList = new ArrayList<>();
        otherUniformList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformRequired");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uniformList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    UniformSell sell = bookDataSnap.getValue(UniformSell.class);
                    uniformList.add(sell);
                }

                UniformRequiredAdapter adapter = new UniformRequiredAdapter(UniformRequiredActivity.this,uniformList);
                uniListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformRequired");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otherUniformList.clear();
                for (DataSnapshot uniDataSnap : snapshot.getChildren()){
                    UniformSell uniSell = uniDataSnap.getValue(UniformSell.class);
                    otherUniformList.add(uniSell);
                }

                UniformRequiredAdapter adapter = new UniformRequiredAdapter(UniformRequiredActivity.this,otherUniformList);
                otherUniListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu_book_req,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_book_req){
            startActivity(new Intent(UniformRequiredActivity.this,UniformRequiredDetailActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}