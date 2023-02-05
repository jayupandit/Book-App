package com.jaypandit.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaypandit.bookapp.master.BookSellActivity;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText edtEmail,edtPass,edtMobileNo,edtConfPass;
    private Button btnSignup;
    private Context mContext;
    int  switchType;

    FirebaseFirestore mStore;
    boolean passVisible;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).hide();

        edtEmail = findViewById(R.id.input_email);
        edtMobileNo = findViewById(R.id.input_mobile_no);
        edtPass = findViewById(R.id.input_pass);
        edtConfPass = findViewById(R.id.input_conf_pass);
        btnSignup = findViewById(R.id.btn_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        edtPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX() >= edtPass.getRight()-edtPass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edtPass.getSelectionEnd();
                        if (passVisible){
                            // set drawable image here
                            edtPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_hide_pass,0);
                            // for hide password
                            edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible = false;
                        }else {
                            // set drawable image here
                            edtPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_show_pass,0);
                            // for Show password
                            edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible = true;
                        }
                        edtPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        final SharedPreferences preferences = getSharedPreferences("Data",Context.MODE_PRIVATE);
        final String type = preferences.getString("Email","");
        if (type.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Login", Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(getApplicationContext(),Enrollment.class);
            startActivity(i);
        }

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtEmail.getText().toString().trim();
                final String mobileNo = edtMobileNo.getText().toString();
                String password = edtPass.getText().toString().trim();
                String confPassword = edtConfPass.getText().toString().trim();


                if (TextUtils.isEmpty(mobileNo)){
                    edtConfPass.setError("Enter Mobile No");
                    return;
                }else if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Email is Required");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    edtPass.setError("Email is Required");
                    return;
                }else if (!password.equals(confPassword)){
                    edtConfPass.setError("Password Not match both field");
                    return;
                } else {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                User mUser = new User(email, mobileNo, password,switchType);
                                database.getReference().child("User").child(mobileNo).setValue(mUser);


                                FirebaseUser fUser = mAuth.getCurrentUser();
                                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext().getApplicationContext(), "Signup successful", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "OnFailure: Email Not Sent" + e.getMessage());
                                    }
                                });

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Email",email);
                                editor.putString("Password",password);
                                editor.putString("mobile",mobileNo);
                                editor.commit();
                                Toast.makeText(mContext, type+"User Created", Toast.LENGTH_SHORT).show();

                                startAdmin();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

    private void startAdmin() {
        Intent i = new Intent(getApplicationContext(), BookSellActivity.class);
        startActivity(i);
    }
}