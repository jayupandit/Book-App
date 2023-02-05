package com.jaypandit.bookapp.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EditBookSellActivity extends AppCompatActivity {

    EditText edtSetName,edtSchoolName,edtClassName,edtBoard;
    Button btnUpdate;
    LinearLayout linearLayout;
    DatabaseReference database;

    int total = 0;

    ArrayList<String> bookList = new ArrayList<>();
    ArrayList<Integer> bookPaidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_sell);

        edtSetName = findViewById(R.id.edt_book_set_name);
        edtSchoolName = findViewById(R.id.edt_book_school_name);
        edtClassName = findViewById(R.id.edt_book_class);
        btnUpdate = findViewById(R.id.btn_book_update);
        edtBoard = findViewById(R.id.edt_book_board);

        linearLayout = findViewById(R.id.book_sell_edt_layout_list);

        Intent i = getIntent();
        String setName = i.getStringExtra("SET-NAME");
        String sclName = i.getStringExtra("SCHOOL-NAME");
        String className = i.getStringExtra("CLASS-NAME");
        String board = i.getStringExtra("BOARD");
        int bookNo = i.getIntExtra("TOTAL-BOOK",0);
        ArrayList<String> arrayList = (ArrayList<String>) i.getSerializableExtra("BOOK-LIST");

        for (int r=0 ; r<arrayList.size();r++){
            addView(r);
        }


        edtSetName.setText(setName);
        edtSchoolName.setText(sclName);
        edtClassName.setText(className);
        edtBoard.setText(board);

        btnUpdate.setOnClickListener(view -> {

            if (checkIfValidAndRed()) {
                String bName = edtSetName.getText().toString();
                String bSclName = edtSchoolName.getText().toString();
                String bClassName = edtClassName.getText().toString();


            BookSell b = new BookSell();
            b.setBookSetName(bName);
            b.setClassName(bClassName);
            b.setSchoolName(bSclName);
            b.setTotal(total);
            b.setBookList(bookList);
            b.setBookPaidList(bookPaidList);
            b.setBookNo(bookNo);
            b.setBoard(edtBoard.getText().toString());

//            updateData(bName,b);

                HashMap h = (HashMap) b.toMap();

                Toast.makeText(this, total+"", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
                String phone = preferences.getString("mobile",null);

                database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("BookSell");
                database.child(bName).updateChildren(h).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditBookSellActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditBookSellActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });



    }

    private boolean checkIfValidAndRed(){

        boolean result = true;

        total = 0;

        for (int i=0;i<linearLayout.getChildCount();i++)
        {
            View bookView = linearLayout.getChildAt(i);

            EditText bookName = bookView.findViewById(R.id.edt_book_name);

            if (!bookName.getText().toString().equals("")){
                bookList.add(bookName.getText().toString());
            } else {
                result = false;
            }
        }

        for (int i=0;i<linearLayout.getChildCount();i++)
        {
            View bookView = linearLayout.getChildAt(i);

            EditText bookPrize = bookView.findViewById(R.id.edt_book_paid);

             if (!bookPrize.getText().toString().equals(""))
            {
                bookPaidList.add(Integer.parseInt(bookPrize.getText().toString()));
            } else {
                bookPaidList.add(0);
            }
        }

        for (int i=0;i<linearLayout.getChildCount();i++){

            View bookView = linearLayout.getChildAt(i);

            EditText bookPaid = bookView.findViewById(R.id.edt_book_paid);

            if(!bookPaid.getText().toString().equals("")) {
                total += Integer.parseInt(bookPaid.getText().toString());
            }
        }


        return result;
    }

    private void addView(int position) {

        Intent i = getIntent();
        ArrayList<String> arrayList = (ArrayList<String>) i.getSerializableExtra("BOOK-LIST");
        ArrayList<String> arrayList1 = (ArrayList<String>) i.getSerializableExtra("BOOK-PAID");

        View bookList = getLayoutInflater().inflate(R.layout.row_add_book_list,null,false);

        String no = String.valueOf(arrayList1.get(position));

        EditText edtBookName = bookList.findViewById(R.id.edt_book_name);
        EditText edtBookPrize = bookList.findViewById(R.id.edt_book_paid);
        CheckBox checkFree = bookList.findViewById(R.id.txt_book_free);

          if (no.equals("0")){
              checkFree.setChecked(true);
              edtBookPrize.setVisibility(View.GONE);
          }else {
              edtBookPrize.setText(String.valueOf(arrayList1.get(position)));
          }

            edtBookName.setText(String.valueOf(arrayList.get(position)));

          checkFree.setOnClickListener(view -> {
              if (checkFree.isChecked()){
                  edtBookPrize.setVisibility(View.GONE);
              }else {
                  edtBookPrize.setVisibility(View.VISIBLE);
              }
          });


        linearLayout.addView(bookList);

    }

}