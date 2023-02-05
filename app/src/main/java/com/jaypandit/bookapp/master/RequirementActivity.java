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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class RequirementActivity extends AppCompatActivity {

    ListView bookSellView,uniSellView,noteSaleView;

    ImageView imgBookReqAdd, imgUniReqAdd, imgNotesReqAdd;

    ArrayList<BookSell> arrayList,nArrayList;
    ArrayList<UniformSell> uArrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_requirement);

        uniSellView = findViewById(R.id.uni_other_list_view);
        bookSellView = findViewById(R.id.book_other_list_view);
        noteSaleView = findViewById(R.id.notes_other_list_view);

        imgBookReqAdd = findViewById(R.id.img_book_req_sale_add);
        imgUniReqAdd = findViewById(R.id.img_uni_req_sale_add);
        imgNotesReqAdd = findViewById(R.id.img_notes_req_sale_add);

        getSupportActionBar().setTitle("Requirement");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        imgNotesReqAdd.setOnClickListener(view -> {
            startActivity(new Intent(this,NotesRequiredDetailActivity.class));
        });

        imgBookReqAdd.setOnClickListener(view -> {
            startActivity(new Intent(this,BookRequiredDetailActivity.class));
        });

        imgUniReqAdd.setOnClickListener(view -> {
            startActivity(new Intent(this,UniformRequiredDetailActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayList = new ArrayList<>();
        uArrayList = new ArrayList<>();
        nArrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("BookRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot bookDataSnap : snapshot.getChildren()){
                    BookSell bookSell = bookDataSnap.getValue(BookSell.class);
                    arrayList.add(bookSell);
                }

                BookRequiredAdapter adapter = new BookRequiredAdapter(RequirementActivity.this,arrayList);
                bookSellView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uArrayList.clear();
                for (DataSnapshot uniDataSnap : snapshot.getChildren()){
                    UniformSell uniSell = uniDataSnap.getValue(UniformSell.class);
                    uArrayList.add(uniSell);
                }

                UniformRequiredAdapter adapter = new UniformRequiredAdapter(RequirementActivity.this,uArrayList);
                uniSellView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookDbRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("NotesRequired");
        bookDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nArrayList.clear();
                for (DataSnapshot uniDataSnap : snapshot.getChildren()){
                    BookSell noteSell = uniDataSnap.getValue(BookSell.class);
                    nArrayList.add(noteSell);
                }

                NotesRequirementAdapter adapter = new NotesRequirementAdapter(RequirementActivity.this,nArrayList);
                noteSaleView.setAdapter(adapter);
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
            startActivity(new Intent(this,OtherRequirementActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}