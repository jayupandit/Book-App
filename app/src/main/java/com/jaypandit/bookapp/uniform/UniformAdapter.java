package com.jaypandit.bookapp.uniform;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class UniformAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Uniform> uniformArrayList;

    public UniformAdapter(Context context,ArrayList<Uniform> arrayList) {

        this.mContext = context;
        this.uniformArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return uniformArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = View.inflate(mContext, R.layout.uniform_list_iteam,null);

        TextView schoolName = view.findViewById(R.id.txt_uni_scl_name);
        TextView paint = view.findViewById(R.id.txt_uni_pant);
        TextView shirt = view.findViewById(R.id.txt_uni_shirt);
        TextView prize = view.findViewById(R.id.txt_uni_prize);
        Spinner spinnerShopName = view.findViewById(R.id.spinner_uni_shop_name);

        schoolName.setText(uniformArrayList.get(i).getSchoolName());
        paint.setText(uniformArrayList.get(i).getPantColor());
        shirt.setText(uniformArrayList.get(i).getShitColor());
        prize.setText(uniformArrayList.get(i).getPrize());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,uniformArrayList.get(i).getShopNameList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShopName.setAdapter(adapter);

        return view;
    }
}
