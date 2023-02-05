package com.jaypandit.bookapp.master;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class UniformRequiredAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<UniformSell> uniformSells;

    public UniformRequiredAdapter(Context mContext, ArrayList<UniformSell> uniformSells) {
        this.mContext = mContext;
        this.uniformSells = uniformSells;
    }

    @Override
    public int getCount() {
        return uniformSells.size();
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

        convertView = View.inflate(mContext, R.layout.uniform_req_list_item,null);

        TextView sclAndClgName = convertView.findViewById(R.id.txt_uni_req_school_name);
        TextView uClassName = convertView.findViewById(R.id.txt_uni_req_class);
        TextView board = convertView.findViewById(R.id.txt_total_uni_req);
        TextView item = convertView.findViewById(R.id.txt_uni_req_);

        sclAndClgName.setText(uniformSells.get(position).getSclName());
        uClassName.setText(uniformSells.get(position).getuClass());
        board.setText(uniformSells.get(position).getBoard());
        item.setText(String.valueOf(uniformSells.get(position).getTotalItem()));

        return convertView;
    }
}
