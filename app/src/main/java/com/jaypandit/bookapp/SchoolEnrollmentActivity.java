package com.jaypandit.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class SchoolEnrollmentActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<School> arrayList;

    DatabaseReference schoolDbRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_enrollment);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_grey)));

        listView = findViewById(R.id.school_listview);

    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid();
        schoolDbRef = FirebaseDatabase.getInstance().getReference("User").child(userid).child("School");
        schoolDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    School school = schoolDataSnap.getValue(School.class);
                    arrayList.add(school);
                }

                SchoolAdapter adapter = new SchoolAdapter(SchoolEnrollmentActivity.this,arrayList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.acitivity_menu_school,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_school){
            startActivity(new Intent(SchoolEnrollmentActivity.this,SchoolDetailActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}