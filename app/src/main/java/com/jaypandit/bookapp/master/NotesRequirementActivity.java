package com.jaypandit.bookapp.master;

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

public class NotesRequirementActivity extends AppCompatActivity {

    ListView noteReqView, otherReqView;

    ArrayList<BookSell> noteReqList, otherReqList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_requirement);

        noteReqView = findViewById(R.id.notes_req_list_view);
        otherReqView = findViewById(R.id.other_notes_req_list_view);

        getSupportActionBar().setTitle("Notes Requirement");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));


    }

    @Override
    protected void onResume() {
        super.onResume();

        noteReqList = new ArrayList<>();
        otherReqList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("NotesRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noteReqList.clear();
                for (DataSnapshot uniDataSnap : snapshot.getChildren()){
                    BookSell noteSell = uniDataSnap.getValue(BookSell.class);
                    noteReqList.add(noteSell);
                }

                NotesRequirementAdapter adapter = new NotesRequirementAdapter(NotesRequirementActivity.this,noteReqList);
                noteReqView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child("NotesRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otherReqList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    otherReqList.add(bookSell);
                }

                NotesRequirementAdapter adapter = new NotesRequirementAdapter(NotesRequirementActivity.this,otherReqList);
                otherReqView.setAdapter(adapter);
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
            startActivity(new Intent(this,NotesRequiredDetailActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}