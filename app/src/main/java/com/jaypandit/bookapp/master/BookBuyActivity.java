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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookBuyActivity extends AppCompatActivity {

//    ImageView imgBookAdd,imgUniformAdd;
    TextView txtBook, txtUniform;
    ListView bookSellView,uniSellView;

    EditText searchView;

    ArrayList<BookSell> arrayList;
    ArrayList<UniformSell> uArrayList;
//    ArrayList<BookSell> nArrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        bookSellView = findViewById(R.id.book_buy_list_view);
        uniSellView = findViewById(R.id.uni_buy_list_view);
//        notesSellView = findViewById(R.id.notes_buy_list_view);
        searchView = findViewById(R.id.search_buy_view);
        txtBook = findViewById(R.id.txt_book);
        txtUniform = findViewById(R.id.txt_uniform);

        arrayList = new ArrayList<>();
        uArrayList = new ArrayList<>();

        getSupportActionBar().setTitle("Book Buy");
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

        ArrayList<BookSell> listFilter = new ArrayList<>();
        ArrayList<UniformSell> listFilter1 = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("BookSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFilter.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    BookSell book = schoolDataSnap.getValue(BookSell.class);
                    if (book.getBookSetName().toLowerCase().contains(toString.toLowerCase()) || book.getBoard().toLowerCase().contains(toString.toLowerCase())
                        || book.getClassName().toLowerCase().contains(toString.toLowerCase())) {
                        listFilter.add(book);
                    }
                }

                BookBuyAdapter adapter = new BookBuyAdapter(BookBuyActivity.this,listFilter);
                bookSellView.setAdapter(adapter);
                adapter.filterList(listFilter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFilter1.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    UniformSell book = schoolDataSnap.getValue(UniformSell.class);
                    if (book.getSetName().toLowerCase().contains(toString.toLowerCase()) || book.getBoard().toLowerCase().contains(toString.toLowerCase())
                            || book.getuClass().toLowerCase().contains(toString.toLowerCase())) {
                        listFilter1.add(book);
                    }
                }

                UniformBuyAdapter adapter = new UniformBuyAdapter(BookBuyActivity.this,listFilter1);
                uniSellView.setAdapter(adapter);
                adapter.filterList(listFilter1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        nArrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("BookSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    arrayList.add(bookSell);
                }

                BookBuyAdapter adapter = new BookBuyAdapter(BookBuyActivity.this,arrayList);
                bookSellView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uArrayList.clear();
                for (DataSnapshot uniDataSnap : snapshot.getChildren()){
                    UniformSell uniSell = uniDataSnap.getValue(UniformSell.class);
                    uArrayList.add(uniSell);
                }

                UniformBuyAdapter adapter = new UniformBuyAdapter(BookBuyActivity.this,uArrayList);
                uniSellView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Notes");
//        bookDbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                arrayList.clear();
//                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
//                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
//                    arrayList.add(bookSell);
//                }
//
//                NotesAdapter adapter = new NotesAdapter(Buy.this,arrayList);
//                notesSellView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}