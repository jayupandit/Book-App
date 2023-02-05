package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.vendor.BookVendor;

import java.util.ArrayList;
import java.util.Locale;

public class PurchaseNewBookActivity extends AppCompatActivity {

    TextView searchView;
    RecyclerView recyclerBookView;

    ArrayList<BookVendor> vendorArrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_new_book);

        searchView = findViewById(R.id.search_buy_view);
        recyclerBookView = findViewById(R.id.recyclerViewNewBook);


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

        vendorArrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("BookVendor");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vendorArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BookVendor bookVendor = dataSnapshot.getValue(BookVendor.class);
                    if(bookVendor.getVendorFullName().toLowerCase().contains(toString.toLowerCase(Locale.ROOT)) || bookVendor.getCityName().toLowerCase().contains(toString.toLowerCase(Locale.ROOT))
                            || bookVendor.getShopAddress().toLowerCase().contains(toString.toLowerCase(Locale.ROOT)) || bookVendor.getShopName().toLowerCase().contains(toString.toLowerCase(Locale.ROOT))) {
                        vendorArrayList.add(bookVendor);
                    }
                }

                NewBookRecycleAdapter adapter = new NewBookRecycleAdapter(PurchaseNewBookActivity.this,vendorArrayList);
                recyclerBookView.setHasFixedSize(true);
                recyclerBookView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                recyclerBookView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}