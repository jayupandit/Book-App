package com.jaypandit.bookapp.uniform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;
import java.util.List;

public class UniformDetailActivity extends AppCompatActivity {

    private EditText schoolName, pantColor,shirtColor,prize;
    private LinearLayout layoutList;
    private Button btnAdd,btnUniformList;

    List<String> shopNameList = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_detail);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_grey)));

        layoutList = findViewById(R.id.shop_layout_list);
        btnAdd = findViewById(R.id.btn_add);
        schoolName = findViewById(R.id.edt_uniform_scl_name);
        pantColor = findViewById(R.id.edt_uniform_pant_color);
        shirtColor = findViewById(R.id.edt_uniform_shirt_color);
        prize = findViewById(R.id.edt_uniform_prize);
        btnUniformList = findViewById(R.id.btn_uniform_list_add);

        database = FirebaseDatabase.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }
        });

        btnUniformList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
                String phone = preferences.getString("mobile",null);

                mAuth = FirebaseAuth.getInstance();

                String sclName = schoolName.getText().toString();
                String pant = pantColor.getText().toString();
                String shirt = shirtColor.getText().toString();
                String uPrize = prize.getText().toString();

                if (sclName.isEmpty()){
                    schoolName.setError("Please Enter Name");
                } else if (pant.isEmpty()){
                    pantColor.setError("please enter color");
                } else if (shirt.isEmpty()){
                    shirtColor.setError("Please enter color");
                } else if (uPrize.isEmpty()){
                    prize.setError("Please Enter Prize");
                } else if(checkIfValidAndRead()){

                    Uniform uniform = new Uniform();
                    uniform.setShopNameList(shopNameList);
                    uniform.setSchoolName(sclName);
                    uniform.setPantColor(pant);
                    uniform.setShitColor(shirt);
                    uniform.setPrize(uPrize);

                    database.getReference().child("User").child(phone).child("Uniform").child(sclName).setValue(uniform);
                    finish();
                }
            }
        });

    }

    private boolean checkIfValidAndRead() {
        shopNameList.clear();
        boolean result = true;

        for (int i=0; i<layoutList.getChildCount();i++){

            View uniformView = layoutList.getChildAt(i);

            EditText shopName = uniformView.findViewById(R.id.edt_shop_name);

            if (!shopName.getText().toString().equals("")){
                shopNameList.add(shopName.getText().toString());
            } else {
                result = false;
            }
        }

        if (shopNameList.size() == 0){
            result = false;
            Toast.makeText(this, "Add Shop Name First!", Toast.LENGTH_SHORT).show();
        } else if (!result){
            Toast.makeText(this, "Enter All Detail correctly !", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView() {

        View shopNameView = getLayoutInflater().inflate(R.layout.row_add_shop_name,null,false);

        EditText edtShopName =  shopNameView.findViewById(R.id.edt_shop_name);
        ImageView imgRemove =  shopNameView.findViewById(R.id.img_remove);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(shopNameView);
            }
        });
        shopNameList.add(edtShopName.getText().toString());
        layoutList.addView(shopNameView);
    }

    private void removeView(View view) {

        layoutList.removeView(view);
    }
}