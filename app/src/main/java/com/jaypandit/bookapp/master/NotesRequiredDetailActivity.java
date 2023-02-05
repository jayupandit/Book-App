package com.jaypandit.bookapp.master;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.databinding.ActivityNotesRequiredDetailBinding;

import java.util.ArrayList;

public class NotesRequiredDetailActivity extends AppCompatActivity {

    private ActivityNotesRequiredDetailBinding binding;

    ArrayList<String> notesNameList, notesTopicName;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityNotesRequiredDetailBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

     notesNameList = new ArrayList<>();
     notesTopicName = new ArrayList<>();

     database = FirebaseDatabase.getInstance();

     binding.btnNotesReqAdd.setOnClickListener(view -> {
         addView();
     });

     binding.btnReqNotesAdd.setOnClickListener(view -> {

         SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
         String phone = preferences.getString("mobile",null);

         if (!validateField()){
             return;
         }

         if (checkIfValidAndRed()) {

             BookSell b = new BookSell();
             b.setSchoolName(binding.reqNotesSchoolName.getText().toString());
             b.setClassName(binding.reqNotesClassName.getText().toString());
             b.setBoard(binding.reqNotesBoard.getText().toString());
             b.setBookList(notesNameList);
             b.setBookTopicList(notesTopicName);
             b.setBookNo(binding.notesReqLayoutList.getChildCount());

             Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

             database.getReference().child("User").child(phone).child("NotesRequired").child(binding.reqNotesSchoolName.getText().toString()).setValue(b);
             database.getReference().child("User").child("NotesRequired").child(binding.reqNotesSchoolName.getText().toString()).setValue(b);
             finish();
         }
     });

    }

    private boolean checkIfValidAndRed() {

        notesNameList.clear();
        notesTopicName.clear();

        boolean result = true;

        for (int i=0; i<binding.notesReqLayoutList.getChildCount() ; i++){

            View notesView = binding.notesReqLayoutList.getChildAt(i);

            EditText bookName = notesView.findViewById(R.id.edt_notes_book_name);

            if (!bookName.getText().toString().equals("")){
                notesNameList.add(bookName.getText().toString());
            } else {
                result = false;
            }
        }

        for (int i=0; i<binding.notesReqLayoutList.getChildCount() ; i++){

            View notesView = binding.notesReqLayoutList.getChildAt(i);

            EditText topicName = notesView.findViewById(R.id.edt_notes_topic_name);

            if (!topicName.getText().toString().equals("")){
                notesTopicName.add(topicName.getText().toString());
            } else {
                result = false;
            }
        }

        if (notesNameList.size() == 0){
            result = false;
            Toast.makeText(this, "dd School Name First!", Toast.LENGTH_SHORT).show();
        } else if (notesTopicName.size() == 0){
            result = false;
            Toast.makeText(this, "dd Topic Name First!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    private boolean validateField() {

        String name = binding.reqNotesSchoolName.getText().toString();
        String className = binding.reqNotesClassName.getText().toString();
        String board = binding.reqNotesBoard.getText().toString();

        if (name.isEmpty()){
            binding.reqNotesSchoolName.setError("Name can not be empty");
            binding.reqNotesSchoolName.requestFocus();
            return false;
        } else if (className.isEmpty()){
            binding.reqNotesClassName.setError("Name can not be empty");
            binding.reqNotesClassName.requestFocus();
            return false;
        } else if (board.isEmpty()){
            binding.reqNotesBoard.setError("Board can not be empty");
            binding.reqNotesBoard.requestFocus();
            return false;
        } else {
            binding.reqNotesSchoolName.setError(null);
            binding.reqNotesBoard.setError(null);
            binding.reqNotesClassName.setError(null);
            return true;

        }
    }

    private void addView() {

        View notesView = getLayoutInflater().inflate(R.layout.row_add_notes_req,null,false);

        ImageView imgRemove = notesView.findViewById(R.id.img_notes_req_remove);

        imgRemove.setOnClickListener(view -> {
            removeBook(notesView);
        });

        binding.notesReqLayoutList.addView(notesView);
    }

    private void removeBook(View notesView) {

        binding.notesReqLayoutList.removeView(notesView);
    }

}