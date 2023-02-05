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

public class UniformSellAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<UniformSell> arrayList;

    DatabaseReference databaseReference;

    public UniformSellAdapter(Context mContext, ArrayList<UniformSell> arrayList) {
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

        convertView = View.inflate(mContext, R.layout.uniform_sell_list,null);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        TextView uniSetName = convertView.findViewById(R.id.txt_uni_set_name);
        TextView uniClass = convertView.findViewById(R.id.txt_uni_sell_class);
        TextView uniSchool = convertView.findViewById(R.id.txt_uni_school_name);
        TextView totalBook = convertView.findViewById(R.id.txt_total_uni);
        TextView totalPrize = convertView.findViewById(R.id.txt_uni_total_prize);
        ImageView imgDelete = convertView.findViewById(R.id.img_uniform_delete);

        uniSetName.setText(arrayList.get(position).getSetName());
        uniClass.setText(arrayList.get(position).getuClass());
        uniSchool.setText(arrayList.get(position).getSclName());
        totalBook.setText(String.valueOf(arrayList.get(position).getTotalItem()));
        totalPrize.setText(String.valueOf(arrayList.get(position).getTotalPrize()));

        SharedPreferences preferences = mContext.getSharedPreferences("Data",mContext.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        imgDelete.setOnClickListener(view -> {
            databaseReference.child(phone).child("UniformSell").child(arrayList.get(position).getSetName()).removeValue();
        });

        convertView.setOnClickListener(view ->{

            Intent i = new Intent(mContext,EditUniformSellActivity.class);
            i.putExtra("SET-NAME",arrayList.get(position).getSetName());
            i.putExtra("SCHOOL-NAME",arrayList.get(position).getSclName());
            i.putExtra("CLASS-NAME",arrayList.get(position).getuClass());
            i.putExtra("SHIRT",arrayList.get(position).getShirtPaid());
            i.putExtra("PANT",arrayList.get(position).getPaintPaid());
            i.putExtra("TIE",arrayList.get(position).getTiePaid());
            i.putExtra("SHOE",arrayList.get(position).getShoesPaid());
            i.putExtra("SHIRT-SIZE",arrayList.get(position).getShirtSize());
            i.putExtra("PANT-SIZE",arrayList.get(position).getPantSize());
            i.putExtra("TIE-SIZE",arrayList.get(position).getTieSize());
            i.putExtra("SHOE-SIZE",arrayList.get(position).getShoesSize());
            i.putExtra("ITEM-NA",arrayList.get(position).getItemNAList());
            i.putExtra("BOARD",arrayList.get(position).getBoard());
            mContext.startActivity(i);
        });

        return convertView;
    }
}
