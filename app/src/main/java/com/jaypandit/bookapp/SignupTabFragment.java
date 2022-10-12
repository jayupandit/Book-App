package com.jaypandit.bookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupTabFragment extends Fragment {

    public static final String TAG = "TAG";
    private EditText edtEmail,edtPass,edtMobileNo,edtConfPass;
    private Button btnSignup;
    private Context mContext;

    FirebaseFirestore  mStore;
    boolean passVisible;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    SignupTabFragment(Context context){
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       ViewGroup view = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragement,container,false);

       edtEmail = view.findViewById(R.id.input_email);
       edtMobileNo = view.findViewById(R.id.input_mobile_no);
       edtPass = view.findViewById(R.id.input_pass);
       edtConfPass = view.findViewById(R.id.input_conf_pass);
       btnSignup = view.findViewById(R.id.btn_sign_up);

       mAuth = FirebaseAuth.getInstance();
       mStore = FirebaseFirestore.getInstance();
       database = FirebaseDatabase.getInstance();

//       if (mAuth.getCurrentUser() != null){
//           startActivity(new Intent(getActivity(),HomeActivity.class));
//           getActivity().finish();
//       }

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

                                User mUser = new User(email,mobileNo,password);
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("User").child(id).setValue(mUser);

                                FirebaseUser fUser = mAuth.getCurrentUser();
                                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getContext().getApplicationContext(), "Signup successful", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "OnFailure: Email Not Sent" + e.getMessage());
                                    }
                                });

                                Toast.makeText(mContext, "User Created", Toast.LENGTH_SHORT).show();
//                                userId = mAuth.getCurrentUser().getUid();
//                                DocumentReference documentReference = mStore.collection("user").document("mobileNo");
//                                Map<String, Object> user = new HashMap<>();
//                                user.put("password", password);
//                                user.put("email", email);
//                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Log.d(TAG, "onSuccess: user profile is created for " + userId);
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.d(TAG, "onFailure: " + e.toString());
//                                    }
//                                });
                                Intent i = new Intent(getActivity(), Enrollment.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getActivity(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

           }
       });

        return view;
    }
//        private void PerformAuth() {
//
//        String email = edtEmail.getText().toString();
//        String mobileNo = edtMobileNo.getText().toString();
//        String pass = edtPass.getText().toString();
//        String confPass = edtConfPass.getText().toString();
//
//        if (){
////            if (!pass.equals(confPass)) {
////                inputConfPass.setError("Password Not match Both field");
////            }else {
//            Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
//
//        } else {
//            Toast.makeText(mContext, "Enter Credentials", Toast.LENGTH_SHORT).show();
//        }

//    }
}
