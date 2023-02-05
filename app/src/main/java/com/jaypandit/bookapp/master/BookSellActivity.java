package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.LoginActivity;
import com.jaypandit.bookapp.MainActivity;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookSellActivity extends AppCompatActivity {

    TextView imgBookAdd,imgUniformAdd;
    ListView bookSellView,uniSellView;

    ArrayList<BookSell> arrayList;
    ArrayList<UniformSell> uArrayList;
//    ArrayList<BookSell> noteArrayList;

    DatabaseReference bookDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_sell);

        imgBookAdd = findViewById(R.id.img_book_sale_add);
        imgUniformAdd = findViewById(R.id.img_uni_sale_add);
        bookSellView = findViewById(R.id.book_sale_list_view);
        uniSellView = findViewById(R.id.uni_sale_list_view);
//        imgNotesAdd = findViewById(R.id.img_notes_sale_add);
//        notesSellView = findViewById(R.id.note_sale_list_view);

        getSupportActionBar().setTitle("Sale");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        imgBookAdd.setOnClickListener(view -> {
            Intent i = new Intent(this,BookSellDetailActivity.class);
            startActivity(i);
        });

        imgUniformAdd.setOnClickListener(view -> {
            Intent i = new Intent(this,UniformSellDetailActivity.class);
            startActivity(i);
        });

//        imgNotesAdd.setOnClickListener(view -> {
//            Intent i = new Intent(this,BookNotesDetail.class);
//            startActivity(i);
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayList = new ArrayList<>();
        uArrayList = new ArrayList<>();
//        noteArrayList = new ArrayList<>();

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

                BookSellAdapter adapter = new BookSellAdapter(BookSellActivity.this,arrayList);
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

                UniformSellAdapter adapter = new UniformSellAdapter(BookSellActivity.this,uArrayList);
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
//                NotesAdapter adapter = new NotesAdapter(BookSellActivity.this,arrayList);
//                notesSellView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_buy_menu,menu);


        MenuItem subMenuItem = menu.findItem(R.id.action_book);
        subMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_book_buy:
                startActivity(new Intent(BookSellActivity.this, BookBuyActivity.class));
                break;
            case R.id.action_book_required:
                startActivity(new Intent(BookSellActivity.this, RequiredBookActivity.class));
                break;
            case R.id.action_purchase_new_book:
                startActivity(new Intent(BookSellActivity.this,PurchaseNewBookActivity.class));
                break;
            case R.id.action_uniform_sale:
                startActivity(new Intent(BookSellActivity.this,UniformSaleActivity.class));
                break;
            case R.id.action_uniform_buy:
                startActivity(new Intent(BookSellActivity.this,UniformBuyActivity.class));
                break;
            case R.id.action_uniform_required:
                startActivity(new Intent(BookSellActivity.this,UniformRequiredActivity.class));
                break;
            case R.id.action_purchase_new_uniform:
                startActivity(new Intent(BookSellActivity.this,NewUniformActivity.class));
                break;
            case R.id.action_notes_sale:
                startActivity(new Intent(BookSellActivity.this,BookNotesActivity.class));
                break;
            case R.id.action_notes_buy:
                startActivity(new Intent(BookSellActivity.this,NotesBuyActivity.class));
                break;
            case R.id.action_notes_required:
                startActivity(new Intent(BookSellActivity.this, NotesRequirementActivity.class));
                break;
            case R.id.action_cart:
                startActivity(new Intent(BookSellActivity.this, CheckCartActivity.class));
                break;
            case R.id.action_my_profile:
                startActivity(new Intent(BookSellActivity.this, MyProfileActivity.class));
                break;
            case R.id.action_logout:
                final SharedPreferences preferences = getApplicationContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}