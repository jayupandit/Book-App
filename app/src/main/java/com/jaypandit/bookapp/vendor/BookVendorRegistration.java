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

public class BookVendorRegistration extends AppCompatActivity {

    EditText vendorFullName,vendorShopName,vendorShopAddress,vendorCityName,vendorPhoneNo,vendorPersonalNo;
    Button btnVendorBook,btnVendorSchool,btnBook;
    LinearLayout vendorBookLayout,vendorSchoolLayout;
    List<String> schoolNameList = new ArrayList<>();
    List<String> bookNameList = new ArrayList<>();

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_vendor_registration);

        vendorFullName = findViewById(R.id.book_vendor_name);
        vendorShopName = findViewById(R.id.book_vendor_shop_name);
        vendorShopAddress = findViewById(R.id.book_vendor_address);
        vendorCityName = findViewById(R.id.book_vendor_city);
        vendorPhoneNo = findViewById(R.id.book_vendor_phone);
        vendorPersonalNo = findViewById(R.id.book_vendor_personal_name);
        btnVendorBook = findViewById(R.id.btn_book_vendor_add);
        btnVendorSchool = findViewById(R.id.btn_book_vendor_school_add);
        btnBook = findViewById(R.id.btn_book_list_add);
        vendorSchoolLayout = findViewById(R.id.book_vendor_school_layout_list);
        vendorBookLayout = findViewById(R.id.book_vendor_layout_list);

        database = FirebaseDatabase.getInstance();

        btnBook.setOnClickListener(view ->{
            validateFiled();
        });

        btnVendorBook.setOnClickListener(view -> addBookView());

        btnVendorSchool.setOnClickListener(view -> addSchoolView());


    }

    private void validateFiled() {

        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        String fullName = vendorFullName.getText().toString();
        String shopName = vendorShopName.getText().toString();
        String shopAddress = vendorShopAddress.getText().toString();
        String cityName = vendorCityName.getText().toString();
        String phoneNo = vendorPhoneNo.getText().toString();
        String personalNo = vendorPersonalNo.getText().toString();

        if (fullName.isEmpty()){
            vendorFullName.setError("Full name can not be empty");
        }else if (shopName.isEmpty()){
            vendorShopName.setError("Shop name can not be empty");
        }else if (shopAddress.isEmpty()){
            vendorShopAddress.setError("Shop Address can not be empty");
        }else if (cityName.isEmpty()){
            vendorCityName.setError("City Name can not be empty");
        }else if (phoneNo.isEmpty()){
            vendorPhoneNo.setError("Phone No can not be empty");
        }else if (personalNo.isEmpty()){
            vendorPersonalNo.setError("personal no. can not be empty");
        } else if (checkIfValidAndRead()){

            BookVendor b = new BookVendor();
            b.setVendorFullName(fullName);
            b.setShopName(shopName);
            b.setShopAddress(shopAddress);
            b.setCityName(cityName);
            b.setPhoneNumber(phoneNo);
            b.setPersonalNumber(personalNo);
            b.setSchoolNameList(schoolNameList);
            b.setBookNameList(bookNameList);

            database.getReference().child("User").child(phone).child("BookVendor").child(fullName).setValue(b);
            finish();
        }

    }

    private boolean checkIfValidAndRead() {
        schoolNameList.clear();
        bookNameList.clear();
        boolean result = true;

        for (int i=0;i<vendorSchoolLayout.getChildCount();i++){

            View schoolView = vendorSchoolLayout.getChildAt(i);

            EditText schoolName = schoolView.findViewById(R.id.edt_shop_name);

            if (!schoolName.getText().toString().equals("")){
                schoolNameList.add(schoolName.getText().toString());
            } else {
                result = false;
            }

        }

        for (int i=0;i<vendorBookLayout.getChildCount();i++){

            View bookView = vendorBookLayout.getChildAt(i);

            EditText bookName = bookView.findViewById(R.id.edt_shop_name);

            if (!bookName.getText().toString().equals("")){
                bookNameList.add(bookName.getText().toString());
            } else {
                result = false;
            }
        }

        if (schoolNameList.size() == 0){
            result = false;
            Toast.makeText(this, "Add School Name First!", Toast.LENGTH_SHORT).show();
        } else if (bookNameList.size() == 0){
            result = false;
            Toast.makeText(this, "Add Book Name First!", Toast.LENGTH_SHORT).show();
        } else if (!result){
            Toast.makeText(this, "Enter All Detail correctly !", Toast.LENGTH_SHORT).show();
        }
        return result;
    }


    private void addSchoolView() {

        View schoolNameView = getLayoutInflater().inflate(R.layout.row_add_shop_name,null,false);

//        EditText edtShopName =  schoolNameView.findViewById(R.id.edt_shop_name);
        ImageView imgRemove =  schoolNameView.findViewById(R.id.img_remove);

        imgRemove.setOnClickListener(view -> removeSchoolView(schoolNameView));
        
        vendorSchoolLayout.addView(schoolNameView);
    }

    private void removeSchoolView(View schoolNameView) {
        vendorSchoolLayout.removeView(schoolNameView);
    }

    private void addBookView() {

        View bookNameView = getLayoutInflater().inflate(R.layout.row_add_shop_name,null,false);

//        EditText edtShopName =  bookNameView.findViewById(R.id.edt_shop_name);
        ImageView imgRemove =  bookNameView.findViewById(R.id.img_remove);

        imgRemove.setOnClickListener(view -> removeBookView(bookNameView));
        
        vendorBookLayout.addView(bookNameView);
    }

    private void removeBookView(View bookNameView) {

        vendorBookLayout.removeView(bookNameView);
    }
}