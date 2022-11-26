package com.jaypandit.bookapp.vendor;

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
import com.jaypandit.bookapp.uniform.Uniform;

import java.util.ArrayList;

public class UniformVendorActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<UniformVendor> arrayList;

    DatabaseReference uniformDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_vendor);

        listView = findViewById(R.id.uniform_vendor_list_view);

        getSupportActionBar().setTitle("Uniform Vendor");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_grey)));

    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        uniformDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformVendor");
        uniformDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot uniformDataSnap : snapshot.getChildren()){
                    UniformVendor school = uniformDataSnap.getValue(UniformVendor.class);
                    arrayList.add(school);
                }

                UniformVendorAdapter adapter = new UniformVendorAdapter(UniformVendorActivity.this,arrayList);
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
        menuInflater.inflate(R.menu.activity_menu_uniform_vendor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_uniform_vendor){
            Intent i = new Intent(this,UniformVendorRegistration.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}