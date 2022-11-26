package com.jaypandit.bookapp.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookEnrollmentActivity extends AppCompatActivity {

    private ListView bookListView;
    private Spinner spinner;
    private ArrayList<Book> arrayList = new ArrayList<>();
    private String bookClass;

    BookAdapter adapter;

    DatabaseReference bookDbRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_enrollment);

        getSupportActionBar().setTitle("Book");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_grey)));

        bookListView = findViewById(R.id.book_listview);
        spinner = findViewById(R.id.spinner_class);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

              String  bookClass = adapterView.getItemAtPosition(i).toString();

                  filter(bookClass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Book");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    Book book = schoolDataSnap.getValue(Book.class);
                        arrayList.add(book);

                }

                adapter = new BookAdapter(BookEnrollmentActivity.this,arrayList);
                bookListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter(String classs) {

        ArrayList<Book> listFilter = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Book");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFilter.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    Book book = schoolDataSnap.getValue(Book.class);
                    if (book.classs.equals(classs)) {
                        listFilter.add(book);
                    } else if (classs.equals("All Class")){
                        listFilter.add(book);
                    }
                }

                adapter.filterList(listFilter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu_book,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_book){
            Intent i = new Intent(BookEnrollmentActivity.this, BookDetailActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
