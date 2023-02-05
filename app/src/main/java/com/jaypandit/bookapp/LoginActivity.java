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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.master.BookSellActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail,edtPass;
    TextView txtFrgPass,txtSignUp;
    Button btnLogin;
    ProgressBar progressBar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean passVisible;
    String mobile;
    String admin = "";

    Switch active;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        edtEmail = findViewById(R.id.email);
        edtPass = findViewById(R.id.pass);
        txtFrgPass = findViewById(R.id.frg_pass);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBarLogin);
        active = findViewById(R.id.swt);
        txtSignUp = findViewById(R.id.txt_sign_up);

        mAuth = FirebaseAuth.getInstance();

        edtPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right =2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX() >= edtPass.getRight()-edtPass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edtPass.getSelectionEnd();
                        if (passVisible){
                            // set drawable image here
                            edtPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_hide_pass,0);
                            // for hide password
                            edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible = false;
                        } else {
                            // set drawable image here
                            edtPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_show_pass,0);
                            // for show password
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

        final SharedPreferences preferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        final String type = preferences.getString("Email","");
        String as = preferences.getString("as","");
        if (type.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Login", Toast.LENGTH_SHORT).show();
        }else if (as.equals("Admin")) {
            Intent i = new Intent(getApplicationContext(), Enrollment.class);
            startActivity(i);
        } else if (as.equals("user")){
            Intent i = new Intent(getApplicationContext(), BookSellActivity.class);
            startActivity(i);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckInternet internet = new CheckInternet();
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();


                if (!internet.isConnected(getApplicationContext())) {
                    showCustomDialog(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Internet Not Connect", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!email.matches(emailPattern)){
                    edtEmail.setError("Enter Connext Email");
                    return;

                } else if (password.isEmpty() || password.length() < 6){
                    edtPass.setError("Enter Proper password");
                    return;
                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){

                                        Query check = FirebaseDatabase.getInstance().getReference("User").orderByChild("email").equalTo(email);
                                        check.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                    mobile = dataSnapshot.child("mobile").getValue(String.class);

                                                }

                                                admin = snapshot.child(mobile).child("as").getValue(String.class);
//                                                Toast.makeText(getApplicationContext(), admin+"", Toast.LENGTH_SHORT).show();

                                                if(snapshot.child(mobile).exists()) {
                                                    if (active.isChecked()) {
                                                        if (admin != null){
                                                            if (admin.equals("Admin")) {
                                                                SharedPreferences.Editor editor = preferences.edit();
                                                                editor.putString("as", "Admin");
                                                                editor.putString("Email", email);
                                                                editor.putString("Password", password);
                                                                editor.putString("mobile", mobile);
                                                                editor.apply();
                                                                Intent i = new Intent(getApplicationContext(), Enrollment.class);
                                                                i.putExtra("mobile", mobile);
                                                                startActivity(i);
                                                        }
                                                        } else {
                                                            progressBar.setVisibility(View.GONE);
                                                            btnLogin.setVisibility(View.VISIBLE);
                                                            Toast.makeText(getApplicationContext(), "You are not admin", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putString("as","user");
                                                        editor.putString("Email",email);
                                                        editor.putString("Password",password);
                                                        editor.putString("mobile",mobile);
                                                        editor.apply();
                                                        Intent i = new Intent(getApplicationContext(), BookSellActivity.class);
                                                        startActivity(i);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_SHORT).show();

                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        btnLogin.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(), "error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }


            }
        });

        txtFrgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });

        txtSignUp.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
    }

    private void showCustomDialog(Context applicationContext) {

        AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
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