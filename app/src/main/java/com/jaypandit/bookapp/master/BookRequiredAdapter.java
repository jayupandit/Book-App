package com.jaypandit.bookapp.master;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookRequiredAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BookSell> arrayList;

    public BookRequiredAdapter(Context mContext, ArrayList<BookSell> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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

        convertView = View.inflate(mContext, R.layout.book_req_list_item,null);

        TextView uniClass = convertView.findViewById(R.id.txt_book_req_class);
        TextView uniSchool = convertView.findViewById(R.id.txt_book_req_school_name);
        TextView totalBook = convertView.findViewById(R.id.txt_total_book_req);
        TextView bookName = convertView.findViewById(R.id.txt_book_req_);

        uniClass.setText(arrayList.get(position).getClassName());
        uniSchool.setText(arrayList.get(position).getSchoolName());
        bookName.setText(String.valueOf(arrayList.get(position).getBookList()));
        totalBook.setText(arrayList.get(position).getBoard());

        return convertView;
    }
}
