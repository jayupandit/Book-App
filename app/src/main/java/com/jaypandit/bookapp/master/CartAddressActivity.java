package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

public class CartAddressActivity extends AppCompatActivity {

    Toolbar toolbar;
    StepView stepView;
    TextView txtAddNewAdd;

    RecyclerView recyclerView;
    AddressRecyclerAdapter.RecyclerViewClickListener listener;

    ArrayList<Address> arrayList;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_address);

        toolbar = findViewById(R.id.cart_address_toolbar);
        stepView = findViewById(R.id.step_view);
        txtAddNewAdd = findViewById(R.id.add_new_address);
        recyclerView = findViewById(R.id.cart_address_list_view);

        toolbar.setTitle("Address");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        stepView.go(1,true);

        arrayList = new ArrayList<>();

        txtAddNewAdd.setOnClickListener( view -> {
            startActivity(new Intent(CartAddressActivity.this,AddNewAddressActivity.class));
        });

        SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("Address");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    Address address = schoolDataSnap.getValue(Address.class);
                    arrayList.add(address);
                }

                setAdapter(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter(ArrayList<Address> addressArrayList) {

        setOnClickListener();
        AddressRecyclerAdapter adapter = new AddressRecyclerAdapter(this, addressArrayList,listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new AddressRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                TextView textView = v.findViewById(R.id.txt_edit);
                RadioButton radioButton = v.findViewById(R.id.radio_btn);
                Button button = v.findViewById(R.id.btn_deliver);

                textView.setVisibility(View.VISIBLE);
                radioButton.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                radioButton.setChecked(true);


                button.setOnClickListener(view ->{
                    startActivity(new Intent(CartAddressActivity.this,CartPaymentActivity.class));
                });
            }
        };

    }
}