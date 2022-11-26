package com.jaypandit.bookapp.school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

public class SchoolDetailActivity extends AppCompatActivity {

    private EditText schoolName,schoolArea,schoolBranch,schoolState,schoolCountry;
    private Spinner spinnerBoard,spinnerBook;
    private Button btnSave;
    private String  schoolBoard, schoolBook;


    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_grey)));

        schoolName = findViewById(R.id.school_name);
        spinnerBoard = findViewById(R.id.school_spinner_board);
        spinnerBook = findViewById(R.id.school_spinner_book);
        schoolArea = findViewById(R.id.school_area);
        schoolBranch = findViewById(R.id.school_branch);
        schoolState = findViewById(R.id.school_state);
        schoolCountry = findViewById(R.id.school_country);
        btnSave = findViewById(R.id.btn_school_save);

        database = FirebaseDatabase.getInstance();

        spinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(schoolBoardPosition == position){
//                    return;
//                } else {
//                    schoolBoardPosition = position;
//                }
                schoolBoard = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                schoolBook = parent.getItemAtPosition(position).toString();
                Toast.makeText(SchoolDetailActivity.this, schoolBook+"", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
                String phone = preferences.getString("mobile",null);

                mAuth = FirebaseAuth.getInstance();

                String name = schoolName.getText().toString();
                String area = schoolArea.getText().toString();
                String branch = schoolBranch.getText().toString();
                String state = schoolState.getText().toString();
                String country = schoolCountry.getText().toString();

                if(name.isEmpty()){
                    schoolName.setError("Enter name");
                } else if (area.isEmpty()){
                    schoolArea.setError("Please Enter Area");
                } else if (branch.isEmpty()){
                    schoolBranch.setError("Please Enter Branch");
                }else if (state.isEmpty()){
                    schoolState.setError("Please Enter State");
                }else if (country.isEmpty()){
                    schoolCountry.setError("Please Enter Country");
                }else {

                    School detail = new School();
                    detail.setSchoolName(name);
                    detail.setSchoolBoard(schoolBoard);
                    detail.setSchoolBook(schoolBook);
                    detail.setSchoolArea(area);
                    detail.setSchoolBranch(branch);
                    detail.setSchoolState(state);
                    detail.setSchoolCountry(country);

                        database.getReference().child("User").child(phone).child("School").child(name).setValue(detail);
                        finish();
                }


            }
        });


    }
}