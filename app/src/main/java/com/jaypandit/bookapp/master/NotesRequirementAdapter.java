package com.jaypandit.bookapp.master;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class NotesRequirementAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BookSell> bookSellArrayList;

    public NotesRequirementAdapter(Context mContext, ArrayList<BookSell> bookSellArrayList) {
        this.mContext = mContext;
        this.bookSellArrayList = bookSellArrayList;
    }

    @Override
    public int getCount() {
        return bookSellArrayList.size();
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

        convertView = View.inflate(mContext, R.layout.notes_req_item_list,null);

        TextView schoolName = convertView.findViewById(R.id.txt_notes_school_name);
        TextView board = convertView.findViewById(R.id.txt_notes_board);
        TextView className = convertView.findViewById(R.id.txt_note_class);
        TextView totalSubject = convertView.findViewById(R.id.txt_note_total_sub);

        schoolName.setText(bookSellArrayList.get(position).getSchoolName());
        board.setText(bookSellArrayList.get(position).getBoard());
        className.setText(bookSellArrayList.get(position).getClassName());
        totalSubject.setText(String.valueOf(bookSellArrayList.get(position).getBookNo()));

        return convertView;
    }
}
