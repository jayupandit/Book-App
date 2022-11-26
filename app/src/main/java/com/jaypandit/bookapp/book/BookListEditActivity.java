package com.jaypandit.bookapp.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaypandit.bookapp.R;

public class BookListEditActivity extends AppCompatActivity {

    private Spinner spinnerBookClass,spinnerBookLanguage;
    private EditText bookName,bookAuthor,bookPublisher,bookPrize;
    private TextView bookPublishYear;
    private Button btnBookUpdate;
    private String bookClass,bookLanguage;
    int languagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist_edit);

        getSupportActionBar().hide();

        bookName = findViewById(R.id.book_name_1);
        bookAuthor = findViewById(R.id.book_author_1);
        bookPublisher = findViewById(R.id.book_publisher_1);
        bookPublishYear = findViewById(R.id.book_publish_Year_1);
        bookPrize = findViewById(R.id.book_prize_1);
        spinnerBookClass = findViewById(R.id.spinner_book_class_1);
        btnBookUpdate = findViewById(R.id.btn_book_update);
        spinnerBookLanguage = findViewById(R.id.spinner_book_language_1);

        String[] bookLanguage = new String[]{
                "Select Language",
                "English",
                "Marathi",
                "Hindi"
        };


        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String publisher = i.getStringExtra("Publisher");
        String author = i.getStringExtra("Author");
        String publishYear = i.getStringExtra("PublishYear");
        String language = i.getStringExtra("Language");
        String prize = i.getStringExtra("Prize");

        for (int b = 0;b<bookLanguage.length;b++) {
            if (bookLanguage[b].equals(language)) {
                languagePosition = b ;
                Toast.makeText(this, b + "", Toast.LENGTH_SHORT).show();
            }
        }

        bookName.setText(name);
        bookAuthor.setText(author);
        bookPublisher.setText(publisher);
        bookPublishYear.setText(publishYear);
        bookPrize.setText(prize);
        spinnerBookLanguage.setSelection(languagePosition);



        spinnerBookLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String language = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(BookListEditActivity.this, language+"", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnBookUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(BookListEditActivity.this, "update", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}