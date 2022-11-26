package com.jaypandit.bookapp.college;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.college.College;

import java.util.ArrayList;

public class CollegeAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<College> collegeList;

    public CollegeAdapter(Context context, ArrayList<College> detailArrayList) {
        mContext = context;
        collegeList = detailArrayList;
    }

    @Override
    public int getCount() {
        return collegeList.size();
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

        convertView = View.inflate(mContext, R.layout.activity_clg_iteam_list,null);

        TextView collegeName = convertView.findViewById(R.id.txt_college_name);
        TextView board = convertView.findViewById(R.id.txt_college_board);
        TextView book = convertView.findViewById(R.id.txt_college_book);
        TextView area = convertView.findViewById(R.id.txt_college_area);
        TextView state = convertView.findViewById(R.id.txt_college_state);
        TextView branch = convertView.findViewById(R.id.txt_college_branch);
        TextView country = convertView.findViewById(R.id.txt_college_country);


        collegeName.setText(collegeList.get(position).getCollegeName());
        board.setText(collegeList.get(position).getBoard());
        book.setText(collegeList.get(position).getBook());
        area.setText(collegeList.get(position).getArea());
        state.setText(collegeList.get(position).getState());
        branch.setText(collegeList.get(position).getBranch());
        country.setText(collegeList.get(position).getCountry());

        return convertView;
    }
}
