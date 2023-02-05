package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class UniformSaleActivity extends AppCompatActivity {

    ImageView imgUniAdd,imgOthUniformAdd;
    ListView uniSellView,othUniSellView;

    ArrayList<UniformSell> uArrayList;
    ArrayList<BookSell> arrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_sale);

        getSupportActionBar().setTitle("Uniform Sale");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        imgUniAdd = findViewById(R.id.img_uniform_sale_add);
        imgOthUniformAdd = findViewById(R.id.img_other_uni_sale_add);
        uniSellView = findViewById(R.id.uniform_sale_list_view);
        othUniSellView = findViewById(R.id.other_uni_sale_list_view);

        imgUniAdd.setOnClickListener(view -> {
            Intent i = new Intent(this,UniformSellDetailActivity.class);
            startActivity(i);
        });

        imgOthUniformAdd.setOnClickListener(view -> {
            Toast.makeText(this, "other uniform", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        uArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformSell");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uArrayList.clear();
                for (DataSnapshot uniDataSnap : snapshot.getChildren()){
                    UniformSell uniSell = uniDataSnap.getValue(UniformSell.class);
                    uArrayList.add(uniSell);
                }

                UniformSellAdapter adapter = new UniformSellAdapter(UniformSaleActivity.this,uArrayList);
                uniSellView.setAdapter(adapter);
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

                BookSellAdapter adapter = new BookSellAdapter(UniformSaleActivity.this,arrayList);
                othUniSellView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}