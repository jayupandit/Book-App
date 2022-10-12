package com.jaypandit.bookapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    private EditText edtNewPass,edtNewConfPass;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        edtNewPass = findViewById(R.id.edt_new_pass);
        edtNewConfPass = findViewById(R.id.edt_conf_new_pass);
        btnUpdate = findViewById(R.id.update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtNewPass.getText().toString().equals(edtNewConfPass.getText().toString())){

                    edtNewConfPass.setError("Password Not match both field");
                } else {
                    setNewPasswordBtn();
                }

            }
        });

    }

    private void setNewPasswordBtn() {

        // check internet connection
        CheckInternet internet = new CheckInternet();
        if (!internet.isConnected(this)){
            showCustomDialog();
            return;
        }

        // Get data from field
        String _newPass = edtNewPass.getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");

        //update Data in Firebase and in session
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(_phoneNumber).child("password").setValue(_newPass);

        startActivity(new Intent(getApplicationContext(),Enrollment.class));
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
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
                });
    }
}