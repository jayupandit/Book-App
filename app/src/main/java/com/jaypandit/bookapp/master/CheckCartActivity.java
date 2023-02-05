package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class CheckCartActivity extends AppCompatActivity {

    TextView txtProductTotalPrice,txtOrderTotalPrice;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnContinue;
    StepView stepView;

    int total = 0;

    DatabaseReference database;
    ArrayList<BookSell> bookSellArrayList;

    int stepIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_cart);

        txtProductTotalPrice = findViewById(R.id.product_total_price);
        txtOrderTotalPrice = findViewById(R.id.order_total_price);
        recyclerView = findViewById(R.id.cart_list_view);
        btnContinue = findViewById(R.id.btn_continue);
        stepView = findViewById(R.id.step_view);
        toolbar = findViewById(R.id.cart_toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String rupee = getResources().getString(R.string.RS);
        String plusSign = getResources().getString(R.string.plus_sign);

        bookSellArrayList = new ArrayList<>();

        txtProductTotalPrice.setText(plusSign+"  "+rupee+" " +String.valueOf(total));
        txtOrderTotalPrice.setText(rupee+"500");

        
        nextStep();

        btnContinue.setOnClickListener(view -> {
            startActivity(new Intent(this,CartAddressActivity.class));
        });

        SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("cart");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookSellArrayList.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    BookSell book = schoolDataSnap.getValue(BookSell.class);
                    bookSellArrayList.add(book);
                }

                for (int i = 0; i<bookSellArrayList.size();i++){
                    total += bookSellArrayList.get(i).getTotal();
                }
                setAdapter(bookSellArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setAdapter(ArrayList<BookSell> bookSellArrayList) {

        BookCartRecycleAdapter adapter = new BookCartRecycleAdapter(this, bookSellArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

//    private void replaceFragment(AddToCartFragment addToCartFragment) {
//
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.cart_frame_layout,addToCartFragment);
//        transaction.commit();
//    }



    private void nextStep() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepIndex++;

//                if (stepIndex < stepsText.length){
//                    textView.setText(stepsText[stepIndex]);
                    stepView.go(0, true);
//                    nextStep();
//                }
            }
        },1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}