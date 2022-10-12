package com.jaypandit.bookapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SchoolAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<School> schoolArrayList;

    public SchoolAdapter(Context context,ArrayList<School> arrayList) {
        mContext = context;
        schoolArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return schoolArrayList.size();
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

        convertView = View.inflate(mContext,R.layout.activity_school_item_list,null);

        TextView schoolName = convertView.findViewById(R.id.txt_school_name);
        TextView board = convertView.findViewById(R.id.txt_school_board);
        TextView book = convertView.findViewById(R.id.txt_school_book);
        TextView area = convertView.findViewById(R.id.txt_school_area);
        TextView state = convertView.findViewById(R.id.txt_school_state);
        TextView branch = convertView.findViewById(R.id.txt_school_branch);
        TextView country = convertView.findViewById(R.id.txt_school_country);

        String[] boardList = new String[]{"Board","CBSC","Nashik Board"};
        String[] bookList = new String[]{"Book","NCRT","o"};

        schoolName.setText(schoolArrayList.get(position).getSchoolName());
        board.setText(boardList[schoolArrayList.get(position).getSchoolBoard()]);
        book.setText(bookList[schoolArrayList.get(position).getSchoolBook()]);
        area.setText(schoolArrayList.get(position).getSchoolArea());
        state.setText(schoolArrayList.get(position).getSchoolState());
        branch.setText(schoolArrayList.get(position).getSchoolBranch());
        country.setText(schoolArrayList.get(position).getSchoolCountry());

        return convertView;
    }
}
