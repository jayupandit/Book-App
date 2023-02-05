package com.jaypandit.bookapp.master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class NotesBuyDetailActivity extends AppCompatActivity {

    TextView txtSetName, txtClgName, txtClassName, txtBoardName;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_buy_detail);

        txtSetName = findViewById(R.id.note_s_set_name);
        txtClgName = findViewById(R.id.txt_clg_scl_name);
        txtClassName = findViewById(R.id.txt_class_name);
        txtBoardName = findViewById(R.id.txt_not_board_name);
        linearLayout = findViewById(R.id.note_set_layout_list);

        Intent i = getIntent();
        String setName = i.getStringExtra("NOTES-SET-NAME");
        String sclName = i.getStringExtra("NOTES-SCHOOL-NAME");
        String className = i.getStringExtra("NOTES-CLASS-NAME");
        String board = i.getStringExtra("NOTES-BOARD");
        ArrayList<String> topicNameList = (ArrayList<String>) i.getSerializableExtra("NOTES-SUB-LIST");
        int bookNo = i.getIntExtra("NOTES-BOOK-NO",0);

        txtSetName.setText(setName);
        txtClgName.setText(sclName);
        txtBoardName.setText(board);
        txtClassName.setText(className);

        for (int r=0 ; r<topicNameList.size();r++){
            addView(r);
        }
    }

    private void addView(int r) {

        Intent i = getIntent();
        ArrayList<String> topicNameList = (ArrayList<String>) i.getSerializableExtra("NOTES-TOP-LIST");
        ArrayList<String> paidList = (ArrayList<String>) i.getSerializableExtra("NOTES-PAID-LIST");
        ArrayList<String> subNameList = (ArrayList<String>) i.getSerializableExtra("NOTES-SUB-LIST");

        View bookList = getLayoutInflater().inflate(R.layout.row_set_notes_detail,null,false);

        TextView subName = bookList.findViewById(R.id.txt_set_title_name);
        TextView topicName = bookList.findViewById(R.id.txt_set_topic_name);
        TextView paid = bookList.findViewById(R.id.txt_set_notes_prize);

        subName.setText(String.valueOf(subNameList.get(r)));
        topicName.setText(String.valueOf(topicNameList.get(r)));
        paid.setText(String.valueOf(paidList.get(r)));

        linearLayout.addView(bookList);
    }
}