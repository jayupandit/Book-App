package com.jaypandit.bookapp.college;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class CollegeEnrollmentActivity extends AppCompatActivity {

    private ArrayList<College> details ;
    private ListView listView;
    DatabaseReference collegeDbRef;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_enrollment);
        getSupportActionBar().setTitle("College");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_grey)));

        listView = findViewById(R.id.clg_listview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        details = new ArrayList<>();
//        String email = mAuth.getCurrentUser().getEmail();
//        if (email != null){
//            Query query = FirebaseDatabase.getInstance().getReference("User").orderByChild("email").equalTo(email);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        String  user1 = dataSnapshot.child("mobile").getValue(String.class);
//                        mobile = user1;
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//            Toast.makeText(CollegeEnrollmentActivity.this, mobile+"", Toast.LENGTH_SHORT).show();
//
//        } else {
//            Toast.makeText(this, "vvvvv", Toast.LENGTH_SHORT).show();
//
        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        collegeDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("college");
        collegeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                details.clear();
                for (DataSnapshot collegeDataSnap : snapshot.getChildren()){
                    College college = collegeDataSnap.getValue(College.class);
                    details.add(college);
                }

                CollegeAdapter adapter = new CollegeAdapter(CollegeEnrollmentActivity.this,details);
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
        menuInflater.inflate(R.menu.activity_menu_college,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_college){
            Intent i = new Intent(CollegeEnrollmentActivity.this, CollegeDetailActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}