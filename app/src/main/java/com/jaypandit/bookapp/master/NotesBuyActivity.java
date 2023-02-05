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

public class NotesBuyActivity extends AppCompatActivity {

    ListView notesView, otherNotesView;

    EditText searchView;

    ArrayList<UniformSell> notesList, otherNotesList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_buy);

        notesView = findViewById(R.id.notes_buy_list_view);
        searchView = findViewById(R.id.search_notes_buy_view);
        otherNotesView = findViewById(R.id.other_notes_buy_list_view);

        getSupportActionBar().setTitle("Notes Buy");
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

        ArrayList<BookSell> notesBuyList, otherBuyList;

        notesBuyList = new ArrayList<>();
        otherBuyList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Notes");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notesBuyList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    if (bookSell.getBookSetName().toLowerCase().contains(toString.toLowerCase()) || bookSell.getBoard().toLowerCase().contains(toString.toLowerCase())
                            || bookSell.getClassName().toLowerCase().contains(toString.toLowerCase())) {
                        notesBuyList.add(bookSell);
                    }
                }

                NotesAdapter adapter = new NotesAdapter(NotesBuyActivity.this,notesBuyList);
                notesView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Notes");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otherBuyList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    if (bookSell.getBookSetName().toLowerCase().contains(toString.toLowerCase()) || bookSell.getBoard().toLowerCase().contains(toString.toLowerCase())
                            || bookSell.getClassName().toLowerCase().contains(toString.toLowerCase())) {
                        otherBuyList.add(bookSell);
                    }
                }

                NotesAdapter adapter = new NotesAdapter(NotesBuyActivity.this,otherBuyList);
                otherNotesView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}