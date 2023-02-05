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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookNotesActivity extends AppCompatActivity {

    ListView noteSaleView,othNoteSaleView;
    ImageView imgNotesAdd,imgOthNotesAdd;

    ArrayList<BookSell> arrayList;
    ArrayList<BookSell> sellArrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_notes);

        noteSaleView = findViewById(R.id.notes_list_view);
        othNoteSaleView = findViewById(R.id.other_notes_sale_list_view);
        imgNotesAdd = findViewById(R.id.img_notes_sale_add);
        imgOthNotesAdd = findViewById(R.id.img_other_notes_sale_add);

        getSupportActionBar().setTitle("Notes Sale");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        imgNotesAdd.setOnClickListener(view -> {
            startActivity(new Intent(BookNotesActivity.this,BookNotesDetail.class));
        });

        imgOthNotesAdd.setOnClickListener(view -> {
            Toast.makeText(this, "Other Notes", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayList = new ArrayList<>();
        sellArrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Notes");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    arrayList.add(bookSell);
                }

                NotesAdapter adapter = new NotesAdapter(BookNotesActivity.this,arrayList);
                noteSaleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("BookSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    arrayList.add(bookSell);
                }

                BookSellAdapter adapter = new BookSellAdapter(BookNotesActivity.this,arrayList);
                othNoteSaleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}