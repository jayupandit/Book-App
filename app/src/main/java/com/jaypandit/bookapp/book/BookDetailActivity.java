package com.jaypandit.bookapp.book;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

public class BookDetailActivity extends AppCompatActivity {

   private Spinner spinnerBookClass,spinnerBookLanguage;
   private EditText bookName,bookAuthor,bookPublisher,bookPrize;
   private TextView bookPublishYear;
   private Button btnBookSave;
   private String bookClass,bookLanguage;

   FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookName = findViewById(R.id.book_name);
        bookAuthor = findViewById(R.id.book_author);
        bookPublisher = findViewById(R.id.book_publisher);
        bookPublishYear = findViewById(R.id.book_publish_Year);
        bookPrize = findViewById(R.id.book_prize);
        spinnerBookClass = findViewById(R.id.spinner_book_class);
        btnBookSave = findViewById(R.id.btn_book_save);
        spinnerBookLanguage = findViewById(R.id.spinner_book_language);

        database = FirebaseDatabase.getInstance();

        spinnerBookClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                bookClass = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerBookLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bookLanguage = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBookSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
                String phone = preferences.getString("mobile",null);

                String name = bookName.getText().toString();
                String author = bookAuthor.getText().toString();
                String publisher = bookPublisher.getText().toString();
                String publisherYear = bookPublishYear.getText().toString();
                String prize = bookPrize.getText().toString();

                if(name.isEmpty()){
                    bookName.setError("Enter name");
                } else if (author.isEmpty()){
                    bookAuthor.setError("Please Enter Area");
                } else if (publisher.isEmpty()){
                    bookPublisher.setError("Please Enter Branch");
                }else if (publisherYear.isEmpty()){
                    bookPublishYear.setError("Please Enter State");
                }else if (prize.isEmpty()){
                    bookPrize.setError("Please Enter Country");
                }else {

                    Book detail = new Book();
                    detail.setBookName(name);
                    detail.setBookAuthor(author);
                    detail.setBookPublisher(publisher);
                    detail.setBookPublishYear(publisherYear);
                    detail.setBookPrize(prize);
                    detail.setClasss(bookClass);
                    detail.setBookLanguage(bookLanguage);

                    database.getReference().child("User").child(phone).child("Book").child(name).setValue(detail);
                    finish();
                }
            }
        });

        bookPublishYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

    }

    public  void showDatePickerDialog(){

        DatePickerDialog dialog = new DatePickerDialog(BookDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                bookPublishYear.setText(day + "/" + (month + 1 ) + "/" +year);
            }
        },2022,05,12);

        dialog.show();
    }
}