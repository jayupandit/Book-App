package com.jaypandit.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CollegeDetailActivity extends AppCompatActivity {

    private EditText collegeName,collegeArea,collegeBranch,collegeState,collegeCountry;
    private Spinner spinnerBoard,spinnerBook;
    private Button btnSave;
    private int collegeBoardPosition = 0;
    private int collegeBookPosition = 0;
    FirebaseDatabase database;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);
        getSupportActionBar().hide();

        collegeName = findViewById(R.id.college_name);
        collegeArea = findViewById(R.id.college_area);
        collegeBranch = findViewById(R.id.college_branch);
        collegeState = findViewById(R.id.college_state);
        collegeCountry = findViewById(R.id.college_country);
        spinnerBoard = findViewById(R.id.college_spinner_board);
        spinnerBook = findViewById(R.id.college_spinner_book);
        btnSave = findViewById(R.id.btn_save);

        database = FirebaseDatabase.getInstance();

        spinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (collegeBoardPosition == position){
                    return;
                } else {
                    collegeBoardPosition = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (collegeBookPosition == position){
                    return;
                } else {
                    collegeBookPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               mAuth = FirebaseAuth.getInstance();
               String userId = mAuth.getCurrentUser().getUid();


                String name = collegeName.getText().toString();
                String area = collegeArea.getText().toString();
                String branch = collegeBranch.getText().toString();
                String state = collegeState.getText().toString();
                String country = collegeCountry.getText().toString();

                if(name.isEmpty()){
                    collegeName.setError("Enter name");
                } else if (area.isEmpty()){
                    collegeArea.setError("Please Enter Area");
                } else if (branch.isEmpty()){
                    collegeBranch.setError("Please Enter Branch");
                }else if (state.isEmpty()){
                    collegeState.setError("Please Enter State");
                }else if (country.isEmpty()){
                    collegeCountry.setError("Please Enter Country");
                }else {

                    College detail = new College();
                    detail.setCollegeName(name);
                    detail.setBoard(collegeBoardPosition);
                    detail.setBook(collegeBookPosition);
                    detail.setArea(area);
                    detail.setBranch(branch);
                    detail.setState(state);
                    detail.setCountry(country);

                    database.getReference().child("User").child(userId).child("college").child(name).setValue(detail);
                    finish();

                }


            }
        });


    }
}