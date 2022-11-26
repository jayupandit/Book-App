package com.jaypandit.bookapp.uniform;

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
import com.jaypandit.bookapp.school.School;

import java.util.ArrayList;

public class UniformActivity extends AppCompatActivity {

    private ListView uniformListView;
    private ArrayList<Uniform> arrayList;

    DatabaseReference uniformDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform);

        getSupportActionBar().setTitle("Uniform");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_grey)));

        uniformListView = findViewById(R.id.uniform_list_view);

    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        uniformDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Uniform");
        uniformDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot uniformDataSnap : snapshot.getChildren()){
                    Uniform school = uniformDataSnap.getValue(Uniform.class);
                    arrayList.add(school);
                }

                UniformAdapter adapter = new UniformAdapter(UniformActivity.this,arrayList);
                uniformListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu_uniform,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_uniform){
            Intent i = new Intent(UniformActivity.this,UniformDetailActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}