package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookRequiredDetailActivity extends AppCompatActivity {

    private EditText edtClgAndSclName, edtClassName, edtBoard;
    private LinearLayout linearLayout;
    private Button btnAdd,btnBookAdd;

    ArrayList<String> stringArrayList = new ArrayList<>();

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_required_detail);

        getSupportActionBar().hide();

        edtClgAndSclName = findViewById(R.id.req_book_school_name);
        edtClassName = findViewById(R.id.req_book_class_name);
        edtBoard = findViewById(R.id.req_book_board);
        btnAdd = findViewById(R.id.btn_req_book_add);
        linearLayout = findViewById(R.id.book_req_layout_list);
        btnBookAdd = findViewById(R.id.btn_book_req_add);

        database = FirebaseDatabase.getInstance();

        btnBookAdd.setOnClickListener(view -> {
            addView();
        });

        btnAdd.setOnClickListener(view -> {

            SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
            String phone = preferences.getString("mobile",null);

            if (!validateField()){
                return;
             }

            if (checkIfValidAndRed()){

                BookSell b = new BookSell();
                b.setSchoolName(edtClgAndSclName.getText().toString());
                b.setClassName(edtClassName.getText().toString());
                b.setBoard(edtBoard.getText().toString());
                b.setBookList(stringArrayList);
                b.setBookNo(linearLayout.getChildCount());

                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

                database.getReference().child("User").child(phone).child("BookRequired").child(edtClgAndSclName.getText().toString()).setValue(b);
                database.getReference().child("User").child("BookRequired").child(edtClgAndSclName.getText().toString()).setValue(b);
                finish();
            }

        });
    }

    private boolean checkIfValidAndRed() {

        stringArrayList.clear();

        boolean result = true;

        for (int i=0; i<linearLayout.getChildCount() ; i++){

            View bookView = linearLayout.getChildAt(i);

            EditText bookName = bookView.findViewById(R.id.edt_shop_name);

            if (!bookName.getText().toString().equals("")){
                stringArrayList.add(bookName.getText().toString());
            } else {
                result = false;
            }
        }

        if (stringArrayList.size() == 0){
            result = false;
            Toast.makeText(this, "dd School Name First!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    private boolean validateField() {

        String name = edtClgAndSclName.getText().toString();
        String className = edtClassName.getText().toString();
        String board = edtBoard.getText().toString();

        if (name.isEmpty()){
            edtClgAndSclName.setError("Name can not be empty");
            edtClgAndSclName.requestFocus();
            return false;
        } else if (className.isEmpty()){
            edtClassName.setError("Name can not be empty");
            edtClassName.requestFocus();
            return false;
        } else if (board.isEmpty()){
            edtBoard.setError("Board can not be empty");
            edtBoard.requestFocus();
            return false;
        } else {
            edtClgAndSclName.setError(null);
            edtBoard.setError(null);
            edtClassName.setError(null);
            return true;

        }
    }


    private void addView() {

        View bookView = getLayoutInflater().inflate(R.layout.row_add_shop_name,null,false);

        EditText bookName = bookView.findViewById(R.id.edt_shop_name);
        ImageView imgRemove = bookView.findViewById(R.id.img_remove);


        imgRemove.setOnClickListener(view -> {
            removeBook(bookView);
        });


        linearLayout.addView(bookView);
    }

    private void removeBook(View bookView) {

        linearLayout.removeView(bookView);
    }
}