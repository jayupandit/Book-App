package com.jaypandit.bookapp.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;
import java.util.List;

public class UniformVendorRegistration extends AppCompatActivity {

    EditText uniformFullName,uniformShopName,uniformShopAddress,uniformCity,uniformPhoneNo,uniformPermanentNo;
    Button btnUniformAdd,btnUniformRegister;
    LinearLayout linearLayout;
    List<String> schoolList = new ArrayList<>();
    List<String> shirtColorList = new ArrayList<>();
    List<String> pantColorList = new ArrayList<>();

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_vendor_registration);

        uniformFullName = findViewById(R.id.uniform_full_name);
        uniformShopName = findViewById(R.id.uniform_shop_name);
        uniformShopAddress = findViewById(R.id.uniform_shop_address);
        uniformCity = findViewById(R.id.uniform_city);
        uniformPhoneNo = findViewById(R.id.uniform_mobile_no);
        uniformPermanentNo = findViewById(R.id.uniform_permanent_no);
        btnUniformAdd = findViewById(R.id.btn_uniform_add);
        btnUniformRegister = findViewById(R.id.btn_uniform_register);
        linearLayout = findViewById(R.id.uniform_layout_list);

        database = FirebaseDatabase.getInstance();


        btnUniformAdd.setOnClickListener(view -> addView());

        btnUniformRegister.setOnClickListener(view -> validateFiled());

    }

    private void validateFiled() {

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        String fullName = uniformFullName.getText().toString();
        String shopName = uniformShopName.getText().toString();
        String shopAddress = uniformShopAddress.getText().toString();
        String shopCity = uniformCity.getText().toString();
        String phoneNo = uniformPhoneNo.getText().toString();
        String permanentNo = uniformPermanentNo.getText().toString();

        if (fullName.isEmpty()){
            uniformFullName.setError("Full name can not be empty");
        }else if (shopName.isEmpty()){
            uniformShopName.setError("Shop name can not be empty");
        }else if (shopAddress.isEmpty()){
            uniformShopAddress.setError("Address can not be empty");
        }else if (shopCity.isEmpty()){
            uniformCity.setError("City can not be empty");
        }else if (phoneNo.isEmpty()){
            uniformPhoneNo.setError("Number can not be empty");
        }else if (permanentNo.isEmpty()){
            uniformPermanentNo.setError("Number can not be empty");
        }else if(checkIfValidAndRed()){

            UniformVendor u = new UniformVendor();
            u.setFullName(fullName);
            u.setShopName(shopName);
            u.setShopAddress(shopAddress);
            u.setShopCity(shopCity);
            u.setShopPhoneNo(phoneNo);
            u.setShopPermanentNo(permanentNo);
            u.setSchoolNameList(schoolList);
            u.setShirtColorList(shirtColorList);
            u.setPantColorList(pantColorList);

            database.getReference().child("User").child(phone).child("UniformVendor").child(fullName).setValue(u);
            finish();
        }
    }

    private boolean checkIfValidAndRed() {
        shirtColorList.clear();
        schoolList.clear();
        pantColorList.clear();

        boolean result = true;

        for (int i=0;i<linearLayout.getChildCount();i++){

            View schoolView = linearLayout.getChildAt(i);

            EditText schoolName = schoolView.findViewById(R.id.edt_school_name);

            if (!schoolName.getText().toString().equals("")) {
                schoolList.add(schoolName.getText().toString());
            } else {
                result = false;
            }

        }

        for (int i=0;i<linearLayout.getChildCount();i++){

            View schoolView = linearLayout.getChildAt(i);

            EditText shirtColor = schoolView.findViewById(R.id.edt_shirt_color);

            if(!shirtColor.getText().toString().equals("")){
                shirtColorList.add(shirtColor.getText().toString());
            }else {
                result = false;
            }

        }

        for (int i=0;i<linearLayout.getChildCount();i++){

            View schoolView = linearLayout.getChildAt(i);

            EditText pantColor = schoolView.findViewById(R.id.edt_pant_color);

            if (!pantColor.getText().toString().equals("")){
                pantColorList.add(pantColor.getText().toString());
            }
            else {
                result = false;
            }

        }



        if (schoolList.size() == 0){
            result = false;
            Toast.makeText(this, "Add School Name First!", Toast.LENGTH_SHORT).show();
        } else if (shirtColorList.size() == 0){
            result = false;
            Toast.makeText(this, "Add Shirt Color First!", Toast.LENGTH_SHORT).show();
        }else if(pantColorList.size() == 0){
            result = false;
            Toast.makeText(this, "Add Pant Color First!", Toast.LENGTH_SHORT).show();
        }else if (!result){
            Toast.makeText(this, "Enter All Detail correctly !", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView() {

        View schoolList = getLayoutInflater().inflate(R.layout.row_add_uniform_school,null,false);

        ImageView imgRemove =  schoolList.findViewById(R.id.img_school_remove);

        imgRemove.setOnClickListener(view -> removeBookView(schoolList)
        );

        linearLayout.addView(schoolList);


    }

    private void removeBookView(View schoolList) {

        linearLayout.removeView(schoolList);
    }
}