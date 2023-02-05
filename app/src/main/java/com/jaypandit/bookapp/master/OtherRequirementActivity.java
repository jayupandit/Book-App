package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class OtherRequirementActivity extends AppCompatActivity {

    ListView listBookReq, listUniReq, listNotesReq;

    ArrayList<BookSell> bookSellArrayList,notesSellArrayList;
    ArrayList<UniformSell> uniformSellArrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_requirement2);

        listBookReq = findViewById(R.id.list_view_book_req);
        listUniReq = findViewById(R.id.list_view_uniform_req);
        listNotesReq = findViewById(R.id.list_view_note_req);

        getSupportActionBar().setTitle("Other Requirement");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        bookSellArrayList = new ArrayList<>();
        notesSellArrayList = new ArrayList<>();
        uniformSellArrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("BookRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookSellArrayList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    bookSellArrayList.add(bookSell);
                }

                BookRequiredAdapter adapter = new BookRequiredAdapter(OtherRequirementActivity.this,bookSellArrayList);
                listBookReq.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uniformSellArrayList.clear();
                for (DataSnapshot uniDataSnap : snapshot.getChildren()){
                    UniformSell uniSell = uniDataSnap.getValue(UniformSell.class);
                    uniformSellArrayList.add(uniSell);
                }

                UniformRequiredAdapter adapter = new UniformRequiredAdapter(OtherRequirementActivity.this,uniformSellArrayList);
                listUniReq.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child("NotesRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notesSellArrayList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    notesSellArrayList.add(bookSell);
                }

                NotesRequirementAdapter adapter = new NotesRequirementAdapter(OtherRequirementActivity.this,notesSellArrayList);
                listNotesReq.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}