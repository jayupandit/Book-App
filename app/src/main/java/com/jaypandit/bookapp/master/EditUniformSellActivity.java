package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class EditUniformSellActivity extends AppCompatActivity {

    EditText setName,schoolName,className,shirt,pant,tie,shoes,edtBoard,shirtSize,pantSize,tieSize,shoesSize;
    CheckBox checkShirtFree,checkPantFree,checkTieFree,checkShoesFree,checkShirtNA,checkPantNA,checkTieNA,checkShoesNA;
    TextView txtShitDisable, txtPantDisable, txtTieDisable, txtShoesDisable;
    Button btnUpdate;

    DatabaseReference database;

    int total = 0;
    int prizeShirt,prizePant,prizeTie,prizeShoes;
    ArrayList<String> itemNAList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_uniform_sell_actitvity);

        setName = findViewById(R.id.edt_uniform_set_name);
        schoolName = findViewById(R.id.edt_uniform_sell__school_name);
        className = findViewById(R.id.edt_uniform_class);
        checkShirtFree = findViewById(R.id.check_edt_shirt_free);
        checkPantFree = findViewById(R.id.check_edt_pant_free);
        checkTieFree = findViewById(R.id.check_edt_tie_free);
        checkShoesFree = findViewById(R.id.check_edt_shoes_free);
        checkShirtNA = findViewById(R.id.check_edt_shirt_NA);
        checkPantNA = findViewById(R.id.check_edt_pant_NA);
        checkTieNA = findViewById(R.id.check_edt_tie_NA);
        checkShoesNA = findViewById(R.id.check_edt_shoes_NA);
        shirtSize = findViewById(R.id.edt_shirtSize);
        pantSize = findViewById(R.id.edt_pantSize);
        tieSize = findViewById(R.id.edt_tieSize);
        shoesSize = findViewById(R.id.edt_shoesSize);

        txtShitDisable = findViewById(R.id.edt_txt_shirt_disable);
        txtPantDisable = findViewById(R.id.edt_txt_pant_disable);
        txtTieDisable = findViewById(R.id.edt_txt_tie_disable);
        txtShoesDisable = findViewById(R.id.edt_txt_shoes_disable);

        shirt = findViewById(R.id.edt_shirt);
        tie = findViewById(R.id.edt_tie);
        pant = findViewById(R.id.edt_pant);
        shoes = findViewById(R.id.edt_shoes);
        edtBoard = findViewById(R.id.edt_uniform_board);
        btnUpdate = findViewById(R.id.btn_uniform_sell_update);

        itemNAList = new ArrayList<>();


        Intent i = getIntent();
        String name = i.getStringExtra("SET-NAME");
        String sclName = i.getStringExtra("SCHOOL-NAME");
        String clsNAMe = i.getStringExtra("CLASS-NAME");
        String shirtPrize = i.getStringExtra("SHIRT");
        String pantPrize = i.getStringExtra("PANT");
        String tiePrize = i.getStringExtra("TIE");
        String shoesPrize = i.getStringExtra("SHOE");
        String shirtS = i.getStringExtra("SHIRT-SIZE");
        String pantS = i.getStringExtra("PANT-SIZE");
        String tieS = i.getStringExtra("TIE-SIZE");
        String shoesS = i.getStringExtra("SHOE-SIZE");
        String board = i.getStringExtra("BOARD");
        itemNAList = (ArrayList<String>) i.getSerializableExtra("ITEM-NA");

        for (int j=0 ; j<itemNAList.size() ; j++) {

            if (itemNAList.get(j).equals("shirt available")) {
                checkShirtNA.setChecked(true);
                shirtSize.setVisibility(View.VISIBLE);
                txtShitDisable.setVisibility(View.GONE);
                shirt.setVisibility(View.VISIBLE);
            } else if (itemNAList.get(j).equals("pant available")) {
                checkPantNA.setChecked(true);
                txtPantDisable.setVisibility(View.GONE);
                pantSize.setVisibility(View.VISIBLE);
                pant.setVisibility(View.VISIBLE);
            } else if (itemNAList.get(j).equals("tie available")) {
                checkTieNA.setChecked(true);
                tie.setVisibility(View.VISIBLE);
                tieSize.setVisibility(View.VISIBLE);
                txtTieDisable.setVisibility(View.GONE);
            } else if (itemNAList.get(j).equals("shoes available")) {
                checkShoesNA.setChecked(true);
                shoes.setVisibility(View.VISIBLE);
                shoesSize.setVisibility(View.VISIBLE);
                txtShoesDisable.setVisibility(View.GONE);
            }
        }

        if ("0".equals(shirtPrize)){
            shirt.setVisibility(View.GONE);
            checkShirtFree.setChecked(true);
            txtShitDisable.setVisibility(View.VISIBLE);
            txtShitDisable.setText("Disable");
        }
        if ("0".equals(pantPrize)){
            pant.setVisibility(View.GONE);
            checkPantFree.setChecked(true);
            txtPantDisable.setVisibility(View.VISIBLE);
            txtPantDisable.setText("Disable");
        }
        if ("0".equals(tiePrize)){
            tie.setVisibility(View.GONE);
            checkTieFree.setChecked(true);
            txtTieDisable.setVisibility(View.VISIBLE);
            txtTieDisable.setText("Disable");
        }
        if ("0".equals(shoesPrize)){
            shoes.setVisibility(View.GONE);
            checkShoesFree.setChecked(true);
            txtShoesDisable.setVisibility(View.VISIBLE);
            txtShoesDisable.setText("Disable");
        }

        getButton();

        setName.setText(name);
        schoolName.setText(sclName);
        className.setText(clsNAMe);
        shirt.setText(shirtPrize);
        tie.setText(tiePrize);
        pant.setText(pantPrize);
        shoes.setText(shoesPrize);
        edtBoard.setText(board);
        shirtSize.setText(shirtS);
        pantSize.setText(pantS);
        tieSize.setText(tieS);
        shoesSize.setText(shoesS);

        btnUpdate.setOnClickListener(view -> {


            SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
            String phone = preferences.getString("mobile",null);

            UniformSell sell = new UniformSell();

            if (!shirt.getText().toString().equals("")) {
                prizeShirt = Integer.parseInt(shirt.getText().toString());
                sell.setShirtPaid(String.valueOf(prizeShirt));
            }
            if (!pant.getText().toString().equals("")) {
                prizePant = Integer.parseInt(pant.getText().toString());
                sell.setPaintPaid(String.valueOf(prizePant));
            }
            if (!tie.getText().toString().equals("")) {
                prizeTie = Integer.parseInt(tie.getText().toString());
                sell.setTiePaid(String.valueOf(prizeTie));
            }
            if (!shoes.getText().toString().equals("")) {
                prizeShoes = Integer.parseInt(shoes.getText().toString());
                sell.setShoesPaid(String.valueOf(prizeShoes));
            }

            if (!shirtSize.getText().toString().equals("")){
                sell.setShirtSize(shirtSize.getText().toString());
            }
            if (!pantSize.getText().toString().equals("")){
                sell.setPantSize(pantSize.getText().toString());
            }
            if (!tieSize.getText().toString().equals("")){
                sell.setTieSize(tieSize.getText().toString());
            }
            if (!shoesSize.getText().toString().equals("")){
                sell.setShoesSize(shoesSize.getText().toString());
            }

            total = prizeShirt + prizeTie + prizePant + prizeShoes ;

            sell.setSetName(setName.getText().toString());
            sell.setSclName(schoolName.getText().toString());
            sell.setuClass(className.getText().toString());
            sell.setShirtPaid(shirt.getText().toString());
            sell.setTiePaid(tie.getText().toString());
            sell.setPaintPaid(pant.getText().toString());
            sell.setShoesPaid(shoes.getText().toString());
            sell.setShirtSize(shirtSize.getText().toString());
            sell.setPantSize(pantSize.getText().toString());
            sell.setTieSize(tieSize.getText().toString());
            sell.setShoesSize(shoesSize.getText().toString());
            sell.setItemNAList(itemNAList);
            sell.setTotalPrize(total);
            sell.setTotalItem(4);
            sell.setBoard(edtBoard.getText().toString());

            database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("UniformSell");
            database.child(name).setValue(sell);
            finish();

        });
    }

    private void getButton() {

        checkShirtNA.setOnClickListener( view -> {
            if (checkShirtNA.isChecked()){
                itemNAList.add("shirt available");
                shirt.setVisibility(View.VISIBLE);
                shirtSize.setVisibility(View.VISIBLE);
                txtShitDisable.setVisibility(View.GONE);
            } else {
                shirt.setVisibility(View.GONE);
                shirt.getText().clear();
                shirtSize.getText().clear();
                shirtSize.setVisibility(View.GONE);
                txtShitDisable.setVisibility(View.VISIBLE);
                itemNAList.remove("shirt available");
            }
        });

        checkPantNA.setOnClickListener( view -> {
            if (checkPantNA.isChecked()){
                itemNAList.add("pant available");
                pant.setVisibility(View.VISIBLE);
                pantSize.setVisibility(View.VISIBLE);
                txtPantDisable.setVisibility(View.GONE);
            }else {
                pant.setVisibility(View.GONE);
                pant.getText().clear();
                pantSize.getText().clear();
                pantSize.setVisibility(View.GONE);
                txtPantDisable.setVisibility(View.VISIBLE);
                itemNAList.remove("pant available");
            }
        });

        checkTieNA.setOnClickListener( view -> {
            if (checkTieNA.isChecked()){
                itemNAList.add("tie available");
                tieSize.setVisibility(View.VISIBLE);
                tie.setVisibility(View.VISIBLE);
                txtTieDisable.setVisibility(View.GONE);
            }else{
                tie.setVisibility(View.GONE);
                tieSize.setVisibility(View.GONE);
                tieSize.getText().clear();
                tie.getText().clear();
                txtTieDisable.setVisibility(View.VISIBLE);
                itemNAList.remove("tie available");
            }
        });

        checkShoesNA.setOnClickListener(view -> {
            if (checkShoesNA.isChecked()){
                itemNAList.add("shoes available");
                shoes.setVisibility(View.VISIBLE);
                shoesSize.setVisibility(View.VISIBLE);
                txtShoesDisable.setVisibility(View.GONE);
            }else{
                shoesSize.setVisibility(View.GONE);
                shoes.setVisibility(View.GONE);
                shoesSize.getText().clear();
                shoes.getText().clear();
                txtShoesDisable.setVisibility(View.VISIBLE);
                itemNAList.remove("shoes available");
            }
        });

        checkShirtFree.setOnClickListener(view -> {
            if (checkShirtFree.isChecked()){
                shirt.setText("0");
                shirt.setVisibility(View.GONE);
                txtShitDisable.setVisibility(View.VISIBLE);
            }else{
                txtShitDisable.setVisibility(View.VISIBLE);
            }
        });

        checkPantFree.setOnClickListener(view -> {
            if (checkPantFree.isChecked()){
                pant.setText("0");
                pant.setVisibility(View.GONE);
                txtPantDisable.setVisibility(View.VISIBLE);
            }else{
                txtPantDisable.setVisibility(View.VISIBLE);
            }
        });

        checkTieFree.setOnClickListener(view -> {
            if (checkTieFree.isChecked()){
                tie.setText("0");
                tie.setVisibility(View.GONE);
                txtTieDisable.setVisibility(View.VISIBLE);
            }else{
                txtTieDisable.setVisibility(View.VISIBLE);
            }
        });

        checkShoesFree.setOnClickListener(view -> {
            if (checkShoesFree.isChecked()){
                shoes.setText("0");
                shoes.setVisibility(View.GONE);
                txtShoesDisable.setVisibility(View.VISIBLE);
            }else{
                txtShoesDisable.setVisibility(View.VISIBLE);
            }
        });
    }
}