package com.jaypandit.bookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EmailOtpVerification extends AppCompatActivity {

    private EditText edtEmailEt,edtOtpEt;
    private AppCompatButton btnEmailOtp;
    int code;
    public static int TIME_OUT = 10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_otp_verification);

        getSupportActionBar().hide();

        edtEmailEt = findViewById(R.id.edt_email_Et);
        edtOtpEt = findViewById(R.id.email_otp_ET);
        btnEmailOtp = findViewById(R.id.btn_email_Otp);


        btnEmailOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckInternet internet = new CheckInternet();
                if (!internet.isConnected(EmailOtpVerification.this)){
                    showCustomDialog();
                    return;
                }

                Random random = new Random();
                code = random.nextInt(8999) + 1000;
                String url = "http://192.168.0.108/jaypandit/sendEmail.php";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EmailOtpVerification.this, "otp"+response, Toast.LENGTH_SHORT).show();
                        edtOtpEt.setVisibility(View.VISIBLE);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EmailOtpVerification.this, "error"+error, Toast.LENGTH_SHORT).show();
                        Log.d("Response",error.toString());
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("email",edtEmailEt.getText().toString());
                        params.put("code",String.valueOf(code));
                        return params;
                    }
                };
                 request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                 requestQueue.add(request);

                String inputCode = edtOtpEt.getText().toString();
                if (inputCode.equals(code)){
                    Toast.makeText(EmailOtpVerification.this, "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EmailOtpVerification.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(EmailOtpVerification.this);
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
