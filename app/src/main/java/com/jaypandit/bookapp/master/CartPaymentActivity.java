package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.jaypandit.bookapp.R;
import com.shuhart.stepview.StepView;

public class CartPaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_payment);

        toolbar = findViewById(R.id.cart_payment_toolbar);
        stepView = findViewById(R.id.step_view);

        toolbar.setTitle("Payment");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stepView.go(2,true);


    }
}