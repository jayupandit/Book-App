package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class UniformRequiredDetailActivity extends AppCompatActivity {

    EditText sclAndClgName, className, board;
    Button btnAddUniList, btnAdd;
    LinearLayout linearLayout;

    ArrayList<String> uniList;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_required_detail);

        sclAndClgName = findViewById(R.id.req_uni_school_name);
        className = findViewById(R.id.req_uni_class_name);
        board = findViewById(R.id.req_uni_board);
        btnAddUniList = findViewById(R.id.btn_req_uni_add);
        linearLayout = findViewById(R.id.uni_req_layout_list);
        btnAdd = findViewById(R.id.btn_uni_req_list_add);

        database = FirebaseDatabase.getInstance();

        getSupportActionBar().hide();

        uniList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        btnAddUniList.setOnClickListener(view -> {
            if (!validateField()){
                return;
            }

            if (checkIfValidAndRed()){

                UniformSell sell = new UniformSell();
                sell.setSclName(sclAndClgName.getText().toString());
                sell.setuClass(className.getText().toString());
                sell.setBoard(board.getText().toString());
                sell.setItemList(uniList);
                sell.setTotalItem(linearLayout.getChildCount());

                database.getReference().child("User").child(phone).child("UniformRequired").child(sclAndClgName.getText().toString()).setValue(sell);
                database.getReference().child("User").child("UniformRequired").child(sclAndClgName.getText().toString()).setValue(sell);
                finish();

            }
        });

        btnAdd.setOnClickListener( view -> {
            addView();
        });

    }

    private boolean validateField() {

        String name = sclAndClgName.getText().toString();
        String uClassName = className.getText().toString();
        String uBoard = board.getText().toString();

        if (name.isEmpty()){
            sclAndClgName.setError("Name can not be empty");
            sclAndClgName.requestFocus();
            return false;
        } else if (uClassName.isEmpty()){
            className.setError("Name can not be empty");
            className.requestFocus();
            return false;
        } else if (uBoard.isEmpty()){
            board.setError("Board can not be empty");
            board.requestFocus();
            return false;
        } else {
            sclAndClgName.setError(null);
            board.setError(null);
            className.setError(null);
            return true;

        }
    }

    private boolean checkIfValidAndRed() {

        boolean result = true;

        for (int i=0; i<linearLayout.getChildCount(); i++){

            View uniView = linearLayout.getChildAt(i);

            EditText edtUniName = uniView.findViewById(R.id.edt_shop_name);

            if (!edtUniName.getText().toString().equals("")){
                uniList.add(edtUniName.getText().toString());
            } else {
                result = false;
            }
        }

        return result;
    }

    private void addView() {

        View uniList = getLayoutInflater().inflate(R.layout.row_add_shop_name,null,false);

        EditText itemName = uniList.findViewById(R.id.edt_shop_name);
        ImageView imageView = uniList.findViewById(R.id.img_remove);

        imageView.setOnClickListener(view -> {
            removeUniform(uniList);
        });

        linearLayout.addView(uniList);
    }

    private void removeUniform(View uniView) {
        linearLayout.removeView(uniView);
    }
}