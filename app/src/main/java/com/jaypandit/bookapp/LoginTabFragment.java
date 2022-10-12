package com.jaypandit.bookapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginTabFragment extends Fragment {

    EditText edtEmail,edtPass;
    TextView txtFrgPass;
    Button btnLogin;
    ProgressBar progressBar;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean passVisible;
//    float v=0;

    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_tab_fragement,container,false);

        edtEmail = view.findViewById(R.id.email);
        edtPass = view.findViewById(R.id.pass);
        txtFrgPass = view.findViewById(R.id.frg_pass);
        btnLogin = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.progressBarLogin);

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckInternet internet = new CheckInternet();
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                if (!internet.isConnected(getContext())) {
                    showCustomDialog(getContext());
                    Toast.makeText(getContext(), "Internet Not Connect", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!email.matches(emailpattern)){
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

                                        Toast.makeText(getActivity(), "Log In Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getActivity(),Enrollment.class);
                                        startActivity(i);
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        btnLogin.setVisibility(View.VISIBLE);
                                        Toast.makeText(getActivity(), "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        txtFrgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ForgotPasswordActivity.class));
            }
        });

        return view;
    }

//    public void letTheUserLogin(){
//
//        if (!validateField()){
//            return;
//        }
//
////        progressBar.setVisibility(View.VISIBLE);
//        // get data
//
//      final    String _Email = edtEmail.getText().toString().trim();
//      final   String _password = edtPass.getText().toString().trim();
//
//        //Database
//
//        Query checkUser = FirebaseDatabase.getInstance().getReference();
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    edtEmail.setError(null);
//
//                    String systemPassword = snapshot.child(_Email).child("password").getValue().toString();
//
//                    if (systemPassword.equals(_password)){
//                        edtPass.setError(null);
//
//                        mAuth.signInWithEmailAndPassword(_Email,_password)
//                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                        if (task.isSuccessful()){
//
//                                            Toast.makeText(getActivity(), "Log In Successful", Toast.LENGTH_SHORT).show();
//                                            startActivity(new Intent(getActivity(),HomeActivity.class));
//                                        } else {
//                                            Toast.makeText(getActivity(), "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    } else {
////                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(getContext(), "Password does not match!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
////                        progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getContext(), "No such user exist!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
////                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

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
                        getActivity().finish();
                    }
                }).show();
    }

//    private boolean validateField(){
//
//        String _email = edtEmail.getText().toString().trim();
//        String _password = edtPass.getText().toString().trim();
//
//        if (_email.isEmpty()){
//            edtEmail.setError("Email can not be empty");
//            edtEmail.requestFocus();
//            return false;
//        } else if (_password.isEmpty()){
//            edtPass.setError("Password can not be empty");
//            edtPass.requestFocus();
//            return false;
//        } else {
//            edtPass.setError(null);
//            edtEmail.setError(null);
//            return true;
//        }
//    }

    //        edtEmail.setTranslationX(800);
//        edtPass.setTranslationX(800);
//        txtFrgPass.setTranslationX(800);
//        btnLogin.setTranslationX(800);
//
//        edtEmail.setAlpha(v);
//        edtPass.setAlpha(v);
//        txtFrgPass.setAlpha(v);
//        btnLogin.setAlpha(v);
//
//        edtEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        edtPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
//        txtFrgPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
//        btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
}
