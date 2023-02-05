package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class UniformSellDetailActivity extends AppCompatActivity {

    EditText setName,schoolName,uniformClass,shirtPaid,pantPaid,tiePaid,shoesPaid,edtBoard,shirtSize,pantSize,tieSize,shoesSize;
    CheckBox checkShirtFree,checkPantFree,checkTieFree,checkShoesFree,checkShirtA,checkPantA,checkTieA,checkShoesA;
    TextView txtShitDisable, txtPantDisable, txtTieDisable, txtShoesDisable;
    Button btnAdd;

    FirebaseDatabase database;

    int totalItem = 4;
    int shirtPrize,pantPrize,tiePrize,shoesPrize,total;
    ArrayList<String> itemNAList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_sell_detail);

        setName = findViewById(R.id.uniform_set_name);
        schoolName = findViewById(R.id.uniform_sell__school_name);
        uniformClass = findViewById(R.id.uniform_class);
        shirtPaid = findViewById(R.id.edt_shirt_paid);
        pantPaid = findViewById(R.id.edt_pant_paid);
        tiePaid = findViewById(R.id.edt_tie_uniform_paid);
        shoesPaid = findViewById(R.id.edt_shoes_uniform_paid);
        checkShirtFree = findViewById(R.id.check_shirt_free);
        checkPantFree = findViewById(R.id.check_pant_free);
        checkTieFree = findViewById(R.id.check_tie_free);
        checkShoesFree = findViewById(R.id.check_shoes_free);
        checkShirtA = findViewById(R.id.check_shirt_NA);
        checkPantA = findViewById(R.id.check_pant_NA);
        checkTieA = findViewById(R.id.check_tie_NA);
        checkShoesA = findViewById(R.id.check_shoes_NA);
        btnAdd = findViewById(R.id.btn_uniform_sell_add);
        edtBoard = findViewById(R.id.uniform_board);
        txtShitDisable = findViewById(R.id.txt_shirt_disable);
        txtPantDisable = findViewById(R.id.txt_pant_disable);
        txtTieDisable = findViewById(R.id.txt_tie_disable);
        txtShoesDisable = findViewById(R.id.txt_shoes_disable);
        shirtSize = findViewById(R.id.edt_shirt_size);
        pantSize = findViewById(R.id.edt_pant_size);
        tieSize = findViewById(R.id.edt_tie_size);
        shoesSize = findViewById(R.id.edt_shoes_size);

        itemNAList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();


        getButton();


        btnAdd.setOnClickListener(view ->{

            SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
            String phone = preferences.getString("mobile",null);

            String uSetName = setName.getText().toString();
            String uSchoolName = schoolName.getText().toString();
            String uClass = uniformClass.getText().toString();

            UniformSell s = new UniformSell();

            if (!shirtPaid.getText().toString().equals("")) {
                shirtPrize = Integer.parseInt(shirtPaid.getText().toString());
                s.setShirtPaid(String.valueOf(shirtPrize));
            }
            if (!pantPaid.getText().toString().equals("")) {
                pantPrize = Integer.parseInt(pantPaid.getText().toString());
                s.setPaintPaid(String.valueOf(pantPrize));
            }
            if (!tiePaid.getText().toString().equals("")) {
                tiePrize = Integer.parseInt(tiePaid.getText().toString());
                s.setTiePaid(String.valueOf(tiePrize));
            }
            if (!shoesPaid.getText().toString().equals("")) {
                shoesPrize = Integer.parseInt(shoesPaid.getText().toString());
                s.setShoesPaid(String.valueOf(shoesPrize));
            }

            if (!shirtSize.getText().toString().equals("")){
                s.setShirtSize(shirtSize.getText().toString());
            }
            if (!pantSize.getText().toString().equals("")){
                s.setPantSize(pantSize.getText().toString());
            }
            if (!tieSize.getText().toString().equals("")){
                s.setTieSize(tieSize.getText().toString());
            }
            if (!shoesSize.getText().toString().equals("")){
                s.setShoesSize(shoesSize.getText().toString());
            }

            total = shirtPrize + pantPrize + tiePrize + shoesPrize;

            s.setSetName(uSetName);
            s.setSclName(uSchoolName);
            s.setuClass(uClass);
            s.setTotalItem(totalItem);
            s.setBoard(edtBoard.getText().toString());
            s.setTotalPrize(total);
            s.setItemNAList(itemNAList);

            database.getReference().child("User").child(phone).child("UniformSell").child(uSetName).setValue(s);
            finish();
        });

    }

    private void getButton() {

        checkShirtA.setOnClickListener( view -> {
            if (checkShirtA.isChecked()){
                shirtPaid.setVisibility(View.VISIBLE);
                shirtSize.setVisibility(View.VISIBLE);
                txtShitDisable.setVisibility(View.GONE);
                itemNAList.add("shirt available");
            } else {
                itemNAList.remove("shirt available");
                shirtPaid.setVisibility(View.GONE);
                shirtSize.setVisibility(View.GONE);
                txtShitDisable.setVisibility(View.VISIBLE);
            }
        });

        checkPantA.setOnClickListener( view -> {
            if (checkPantA.isChecked()){
                pantPaid.setVisibility(View.VISIBLE);
                pantSize.setVisibility(View.VISIBLE);
                txtPantDisable.setVisibility(View.GONE);
                itemNAList.add("pant available");
            }else {
                itemNAList.remove("pant available");
                pantPaid.setVisibility(View.GONE);
                pantSize.setVisibility(View.GONE);
                txtPantDisable.setVisibility(View.VISIBLE);
            }
        });

        checkTieA.setOnClickListener( view -> {
            if (checkTieA.isChecked()){
                tiePaid.setVisibility(View.VISIBLE);
                tieSize.setVisibility(View.VISIBLE);
                txtTieDisable.setVisibility(View.GONE);
                itemNAList.add("tie available");
            }else{
                itemNAList.remove("tie available");
                tiePaid.setVisibility(View.GONE);
                tieSize.setVisibility(View.GONE);
                txtTieDisable.setVisibility(View.VISIBLE);
            }
        });

        checkShoesA.setOnClickListener(view -> {
            if (checkShoesA.isChecked()){
                shoesPaid.setVisibility(View.VISIBLE);
                shoesSize.setVisibility(View.VISIBLE);
                txtShoesDisable.setVisibility(View.GONE);
                itemNAList.add("shoes available");
            }else{
                itemNAList.remove("shoes available");
                shoesPaid.setVisibility(View.GONE);
                shoesSize.setVisibility(View.GONE);
                txtShoesDisable.setVisibility(View.VISIBLE);
            }
        });

        checkShirtFree.setOnClickListener(view -> {
            if (checkShirtFree.isChecked()){
                shirtPaid.setText("0");
                shirtPaid.setVisibility(View.GONE);
                txtShitDisable.setVisibility(View.VISIBLE);
            }else{
                txtShitDisable.setVisibility(View.VISIBLE);
            }
        });

        checkPantFree.setOnClickListener(view -> {
            if (checkPantFree.isChecked()){
                pantPaid.setText("0");
                pantPaid.setVisibility(View.GONE);
                txtPantDisable.setVisibility(View.VISIBLE);
            }else{
                txtPantDisable.setVisibility(View.VISIBLE);
            }
        });

        checkTieFree.setOnClickListener(view -> {
            if (checkTieFree.isChecked()){
                tiePaid.setText("0");
                tiePaid.setVisibility(View.GONE);
                txtTieDisable.setVisibility(View.VISIBLE);
                txtTieDisable.setText("Disable");
            }else{
                txtTieDisable.setVisibility(View.VISIBLE);
            }
        });

        checkShoesFree.setOnClickListener(view -> {
            if (checkShoesFree.isChecked()){
                shoesPaid.setText("0");
                shoesPaid.setVisibility(View.GONE);
                txtShoesDisable.setVisibility(View.VISIBLE);
                txtShoesDisable.setText("Disable");
            }else{
                txtShoesDisable.setVisibility(View.VISIBLE);
            }
        });
    }


}