package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;
import java.util.List;

public class BookSellDetailActivity extends AppCompatActivity {

    EditText bookSetName,schoolName,className,sclBoard;
    TextView totalPrize;
    ImageView imgRefresh;
    LinearLayout linearLayout;
    Button btnBookAdd,btnBookSellAdd;
    int total = 0;
    int bookNoo = 0;
    int v;

    ArrayList<String> bookList = new ArrayList<>();
    ArrayList<Integer> bookPaidList = new ArrayList<>();
    ArrayList<String> bookFreeList = new ArrayList<>();

    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_sell_detail);

        bookSetName = findViewById(R.id.book_set_name);
        schoolName = findViewById(R.id.book_school_name);
        className = findViewById(R.id.book_class);
        totalPrize = findViewById(R.id.book_total_prize);
        linearLayout = findViewById(R.id.book_sell_layout_list);
        btnBookAdd = findViewById(R.id.btn_book_add);
        btnBookSellAdd = findViewById(R.id.btn_book_sell_add);
        imgRefresh = findViewById(R.id.img_refresh);
        sclBoard = findViewById(R.id.book_board);
        database = FirebaseDatabase.getInstance();

        btnBookSellAdd.setOnClickListener(view -> addView());

        imgRefresh.setOnClickListener(view -> {
            if (checkIfValidAndRed()){
                Toast.makeText(this, total +"", Toast.LENGTH_SHORT).show();
            }
        });


        btnBookAdd.setOnClickListener(v -> {

            SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
            String phone = preferences.getString("mobile",null);

            String bookName = bookSetName.getText().toString();
            String sclName = schoolName.getText().toString();
            String clsName = className.getText().toString();
            String board = sclBoard.getText().toString();

            if (bookName.isEmpty()){
                bookSetName.setError("book name can not be empty");
            }else if (sclName.isEmpty()){
                schoolName.setError("school name can not be empty");
            }else if (clsName.isEmpty()){
                className.setError("class name can not be empty");
            } else if (board.isEmpty()){
                sclBoard.setError("Board name can not be empty");
            }
            else {
                Toast.makeText(this, total+"", Toast.LENGTH_SHORT).show();

                BookSell bookSell = new BookSell();
                bookSell.setBookSetName(bookName);
                bookSell.setSchoolName(sclName);
                bookSell.setClassName(clsName);
                bookSell.setBookList(bookList);
                bookSell.setBookFreeList(bookFreeList);
                bookSell.setBookPaidList(bookPaidList);
                bookSell.setTotal(total);
                bookSell.setBookNo(linearLayout.getChildCount());
                bookSell.setBoard(board);

                Toast.makeText(this, total+"", Toast.LENGTH_SHORT).show();
                database.getReference().child("User").child(phone).child("BookSell").child(bookName).setValue(bookSell);
                database.getReference().child("User").child("BookSell").child(bookName).setValue(bookSell);
                finish();
            }

        });




    }

    private boolean checkIfValidAndRed() {
        bookList.clear();
        bookFreeList.clear();
        bookPaidList.clear();

        boolean result = true;

        total = 0;

        bookNoo = 0;

        for (int i=0;i<linearLayout.getChildCount();i++){

            View bookView = linearLayout.getChildAt(i);

            EditText bookName = bookView.findViewById(R.id.edt_book_name);

            if (!bookName.getText().toString().equals("")) {
                bookList.add(bookName.getText().toString());
            } else {
                result = false;
            }

        }

        for (int i=0;i<linearLayout.getChildCount();i++){

            View bookView = linearLayout.getChildAt(i);

             EditText bookPaid = bookView.findViewById(R.id.edt_book_paid);
             CheckBox bookFree = bookView.findViewById(R.id.txt_book_free);


            if(!bookPaid.getText().toString().equals("")){
                bookPaidList.add(Integer.parseInt(bookPaid.getText().toString()));
            } else {
                Toast.makeText(this, "jk", Toast.LENGTH_SHORT).show();
                bookPaidList.add(0);
            }


        }

//        for (int i=0;i<linearLayout.getChildCount();i++){
//
//            View bookView = linearLayout.getChildAt(i);
//
//            TextView bookNumber = bookView.findViewById(R.id.book_number);
//
//            bookNoo = i + 1;
//            bookNumber.setText(String.valueOf(bookNoo));
//
//
//
//        }

        for (int i=0;i<linearLayout.getChildCount();i++){

            View bookView = linearLayout.getChildAt(i);

            EditText bookPaid = bookView.findViewById(R.id.edt_book_paid);

            if(!bookPaid.getText().toString().equals("")) {
                total += Integer.parseInt(bookPaid.getText().toString());
                totalPrize.setText(String.valueOf(total));
            }
        }

        Toast.makeText(this, bookNoo+"", Toast.LENGTH_SHORT).show();


        if (bookList.size() == 0){
            result = false;
            Toast.makeText(this, "Add School Name First!", Toast.LENGTH_SHORT).show();
        }else if (!result){
            Toast.makeText(this, "Enter All Detail correctly !", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView() {

        View bookList = getLayoutInflater().inflate(R.layout.row_add_book_list,null,false);

        EditText edtBookName = bookList.findViewById(R.id.edt_book_name);
        ImageView imageView = bookList.findViewById(R.id.img_book_remove);
        EditText edtBookPaid = bookList.findViewById(R.id.edt_book_paid);
        CheckBox checkFree = bookList.findViewById(R.id.txt_book_free);

        checkFree.setOnClickListener(view -> {
        if (checkFree.isChecked()){
            edtBookPaid.setVisibility(View.GONE);
        } else {
            edtBookPaid.setVisibility(View.VISIBLE);
        }
        });

        imageView.setOnClickListener(view -> {
            total = 0;
            removeBookView(bookList);});


        linearLayout.addView(bookList);


    }

    private void removeBookView(View bookList) {
        linearLayout.removeView(bookList);
    }
}