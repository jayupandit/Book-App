package com.jaypandit.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnSend;
    ProgressBar progressBar;
    EditText etPhone;
    FirebaseAuth mAuth;
    String email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);
        btnSend = findViewById(R.id.btnSend);
        progressBar = findViewById(R.id.progressBar);
        etPhone = findViewById(R.id.etPhone);

        mAuth = FirebaseAuth.getInstance();

        final SharedPreferences preferences = getSharedPreferences("Data",Context.MODE_PRIVATE);
        final String type = preferences.getString("Email","");
        if (type.isEmpty()){
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(getApplicationContext(),Enrollment.class);
            startActivity(i);
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckInternet internet = new CheckInternet();
                if (!internet.isConnected(getApplicationContext())){
                    showCustomDialog(getApplicationContext());
                    return;
                }

                //validate phone Number
                if(!validateFields()){
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //get data from fields
               final String _phoneNumber = etPhone.getText().toString().trim();

             //  Check weather User exist or not in database
              Query checkUser = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("mobile").equalTo(_phoneNumber);
              checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      //if phone number exists the call otp to verify that it is his/her phone number
                      if (snapshot.exists()){
                          etPhone.setError(null);
                          for (DataSnapshot sup : snapshot.getChildren()){
                              User user = sup.getValue(User.class);
                              email = user.email;
                              password = user.password;
                          }
                          SharedPreferences.Editor editor = preferences.edit();
                          editor.putString("Email",email);
                          editor.putString("Password",password);
                          editor.putString("mobile",_phoneNumber);
                          editor.commit();
                          Intent i = new Intent(getApplicationContext(),OTPPhoneVerificationActivity.class);
                          i.putExtra("phone",_phoneNumber);
                          i.putExtra("whatToDO","updateData");
                          startActivity(i);
                          progressBar.setVisibility(View.GONE);
                      } else {
                          progressBar.setVisibility(View.GONE);
                          etPhone.setError("No Such user exist");
                          etPhone.requestFocus();
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                      Toast.makeText(ForgotPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                  }
              });


            }
        });

    }

    private boolean validateFields() {
        String phone = etPhone.getText().toString().trim();
        if (phone.isEmpty()){
            etPhone.setError("Field can not be empty");
            return false;
        } else {
            etPhone.setError(null);
            return true;
        }
    }

    private void showCustomDialog(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(true)
                .setPositiveButton("connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

}