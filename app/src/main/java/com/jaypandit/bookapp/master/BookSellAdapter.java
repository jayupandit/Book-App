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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookSellAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BookSell> sellArrayList;

    DatabaseReference databaseReference;

    public BookSellAdapter(Context mContext, ArrayList<BookSell> sellArrayList) {
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

        convertView = View.inflate(mContext, R.layout.booksell_iteam_list,null);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        TextView bookSetName = convertView.findViewById(R.id.txt_book_set_name);
        TextView bookClass = convertView.findViewById(R.id.txt_book_sell_class);
        TextView bookSchool = convertView.findViewById(R.id.txt_book_school_name);
        TextView totalBook = convertView.findViewById(R.id.txt_total_book);
        TextView totalPrize = convertView.findViewById(R.id.txt_book_total_prize);
        ImageView imgDelete = convertView.findViewById(R.id.img_book_delete);

        SharedPreferences preferences = mContext.getSharedPreferences("Data",mContext.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        bookSetName.setText(sellArrayList.get(position).getBookSetName());
        bookClass.setText(sellArrayList.get(position).getClassName());
        bookSchool.setText(sellArrayList.get(position).getSchoolName());
        totalBook.setText(String.valueOf(sellArrayList.get(position).getBookNo()));
        totalPrize.setText(String.valueOf(sellArrayList.get(position).getTotal()));


        imgDelete.setOnClickListener(view -> {
            databaseReference.child(phone).child("BookSell").child(sellArrayList.get(position).getBookSetName()).removeValue();
        });

        convertView.setOnClickListener(view -> {

            Intent i = new Intent(mContext,EditBookSellActivity.class);
            i.putExtra("SET-NAME",sellArrayList.get(position).getBookSetName());
            i.putExtra("SCHOOL-NAME",sellArrayList.get(position).getSchoolName());
            i.putExtra("CLASS-NAME",sellArrayList.get(position).getClassName());
            i.putExtra("BOOK-LIST", sellArrayList.get(position).getBookList());
            i.putExtra("BOOK-PAID", sellArrayList.get(position).getBookPaidList());
            i.putExtra("TOTAL-BOOK",sellArrayList.get(position).getBookNo());
            i.putExtra("BOARD",sellArrayList.get(position).getBoard());
            mContext.startActivity(i);

        });

        return convertView;
    }
}
