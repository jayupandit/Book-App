package com.jaypandit.bookapp.master;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BookSell> sellArrayList;

    DatabaseReference databaseReference;

    public NotesAdapter(Context mContext, ArrayList<BookSell> sellArrayList) {
        this.mContext = mContext;
        this.sellArrayList = sellArrayList;
    }

    @Override
    public int getCount() {
        return sellArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(mContext, R.layout.notes_item_list,null);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        SharedPreferences preferences = mContext.getSharedPreferences("Data",mContext.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        TextView noteSetName = convertView.findViewById(R.id.txt_note_set_name);
        TextView noteClass = convertView.findViewById(R.id.txt_note_sell_class);
        TextView noteSchool = convertView.findViewById(R.id.txt_note_school_name);
        TextView totalNote = convertView.findViewById(R.id.txt_total_note);
        TextView totalPrize = convertView.findViewById(R.id.txt_note_total_prize);
        ImageView imgAdd = convertView.findViewById(R.id.img_notes_add_card_yes);
        ImageView imgRemove = convertView.findViewById(R.id.img_notes_add_card_no);

        imgAdd.setOnClickListener(view ->{
            databaseReference.child(phone).child("card").child(sellArrayList.get(position).getBookSetName()).setValue(sellArrayList.get(position));
            imgAdd.setVisibility(View.GONE);
            imgRemove.setVisibility(View.VISIBLE);
        });

        imgRemove.setOnClickListener(view ->{
            databaseReference.child(phone).child("card").child(sellArrayList.get(position).getBookSetName()).removeValue();
            imgAdd.setVisibility(View.VISIBLE);
            imgRemove.setVisibility(View.GONE);
        });

        noteSetName.setText(sellArrayList.get(position).getBookSetName());
        noteClass.setText(sellArrayList.get(position).getClassName());
        noteSchool.setText(sellArrayList.get(position).getSchoolName());
        totalNote.setText(String.valueOf(sellArrayList.get(position).getBookNo()));
        totalPrize.setText(String.valueOf(sellArrayList.get(position).getTotal()));

        convertView.setOnClickListener(view ->{

            Intent i = new Intent(mContext,NotesBuyDetailActivity.class);
            i.putExtra("NOTES-SET-NAME",sellArrayList.get(position).getBookSetName());
            i.putExtra("NOTES-CLASS-NAME",sellArrayList.get(position).getClassName());
            i.putExtra("NOTES-SCHOOL-NAME",sellArrayList.get(position).getSchoolName());
            i.putExtra("NOTES-BOARD",sellArrayList.get(position).getBoard());
            i.putExtra("NOTES-BOOK-NO",sellArrayList.get(position).getBookNo());
            i.putExtra("NOTES-TOP-LIST",sellArrayList.get(position).getBookTopicList());
            i.putExtra("NOTES-PAID-LIST",sellArrayList.get(position).getBookPaidList());
            i.putExtra("NOTES-SUB-LIST",sellArrayList.get(position).getBookList());
            mContext.startActivity(i);

        });

        return convertView;
    }
}
