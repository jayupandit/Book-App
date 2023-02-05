package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;
import com.shuhart.stepview.StepView;

import java.util.Objects;

public class AddNewAddressActivity extends AppCompatActivity {

    EditText edtFullName,edtPhoneNo,edtHouseNo,edtColony, edtPinCode, edtCity, edtNearbyLocation;
    Button btnSave;
    Spinner spinner;
    Toolbar toolbar;
    StepView stepView;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        edtFullName = findViewById(R.id.full_name);
        edtPhoneNo = findViewById(R.id.edt_phone_no);
        edtHouseNo = findViewById(R.id.edt_house_no);
        edtColony = findViewById(R.id.edt_colony);
        edtPinCode = findViewById(R.id.edt_pin_code);
        edtCity = findViewById(R.id.edt_city);
        edtNearbyLocation = findViewById(R.id.edt_nearby_location);
        btnSave = findViewById(R.id.btn_save_address);
        spinner = findViewById(R.id.spinner_state);
        stepView = findViewById(R.id.step_view);
        toolbar = findViewById(R.id.cart_Add_toolbar);

        toolbar.setTitle("Add New Address");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stepView.go(1,true);

        database = FirebaseDatabase.getInstance();

        btnSave.setOnClickListener(view -> {

            Address address = new Address();
            address.setFullName(edtFullName.getText().toString());
            address.setPhoneNumber(edtPhoneNo.getText().toString());
            address.setHouseNo(edtHouseNo.getText().toString());
            address.setColony(edtColony.getText().toString());
            address.setPinCode(edtPinCode.getText().toString());
            address.setCity(edtCity.getText().toString());
            address.setNearbyLocation(edtNearbyLocation.getText().toString());

            SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
            String phone = preferences.getString("mobile",null);

            database.getReference().child("User").child(phone).child("Address").child(edtFullName.getText().toString()).setValue(address);
            finish();

        });


    }
}