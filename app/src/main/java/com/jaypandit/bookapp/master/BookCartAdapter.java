package com.jaypandit.bookapp.master;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookCartAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BookSell> arrayList;

    public BookCartAdapter(Context mContext, ArrayList<BookSell> arrayList) {
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

        convertView = ViewGroup.inflate(mContext, R.layout.book_cart_item_list,null);

        TextView txtSetName = convertView.findViewById(R.id.txt_book_cart_set_name);
        TextView txtClassName = convertView.findViewById(R.id.txt_book_cart_class);
        TextView txtBoard = convertView.findViewById(R.id.txt_total_book_cart);
        TextView txtPrice = convertView.findViewById(R.id.txt_book_cart_price);
        TextView txtTotalItem = convertView.findViewById(R.id.txt_book_cart_total_item);
//        TextView txtRemove = convertView.findViewById(R.id.txt_book_cart_remove);

        txtSetName.setText(arrayList.get(position).getBookSetName());
        txtClassName.setText(arrayList.get(position).getClassName());
        txtBoard.setText(arrayList.get(position).getBoard());
        txtPrice.setText(String.valueOf(arrayList.get(position).getTotal()));
        txtTotalItem.setText(String.valueOf(arrayList.get(position).getBookNo()));

        return convertView;
    }
}
