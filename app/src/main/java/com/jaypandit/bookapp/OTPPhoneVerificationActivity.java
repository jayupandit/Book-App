package com.jaypandit.bookapp;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


import java.util.concurrent.TimeUnit;

public class OTPPhoneVerificationActivity extends AppCompatActivity {

    private String codeBySystem;
    TextView tvMobile,tvResendBtn;
    Button btnVerify;
    PinView pinView;
    ProgressBar progressBarVerify;
    String phone,whatToDO,userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_phone_otp_verify);

        tvMobile = findViewById(R.id.tvMobile);
        tvResendBtn = findViewById(R.id.tvResendBtn);
        btnVerify = findViewById(R.id.btnVerify);
        pinView = findViewById(R.id.pinview);
        progressBarVerify = findViewById(R.id.progressBarVerify);


        tvMobile.setText(String.format(
                "+91-%s", getIntent().getStringExtra("phone")
        ));

        phone = getIntent().getStringExtra("phone");
        whatToDO = getIntent().getStringExtra("whatToDO");

        tvResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OTPPhoneVerificationActivity.this, "OTP Send Successfully.", Toast.LENGTH_SHORT).show();
            }
        });
        
        sendVerificationCodeToUser(phone);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarVerify.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.INVISIBLE);

                   String code = pinView.getText().toString();
                   if (!code.isEmpty()){
                       verifyCode(code);
                   }

                }
        });


    }

    private void sendVerificationCodeToUser(String phone) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber("+91"+ phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                    Toast.makeText(OTPPhoneVerificationActivity.this, "Otp Successful send", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String otpCode = phoneAuthCredential.getSmsCode();
                    if(otpCode != null){
                        verifyCode(otpCode);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(OTPPhoneVerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String otpCode) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,otpCode);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if (whatToDO.equals("updateData")){
                                updateOldUserData();
                            }
                            Toast.makeText(OTPPhoneVerificationActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPPhoneVerificationActivity.this, "Verification Not completed! Try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

    private void updateOldUserData() {
        String u = FirebaseDatabase.getInstance().getReference("User").push().getKey();
        Toast.makeText(this, u+"", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(),SetNewPassword.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("mobile",phone);
        startActivity(i);
        finish();
    }

}
