package com.jaypandit.bookapp.master;

import android.content.Context;
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

public class UniformBuyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<UniformSell> arrayList;

    DatabaseReference databaseReference;

    public UniformBuyAdapter(Context mContext, ArrayList<UniformSell> arrayList) {
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

        convertView = View.inflate(mContext, R.layout.activity_uniform_buy_list,null);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        SharedPreferences preferences = mContext.getSharedPreferences("Data",mContext.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        TextView uniSetName = convertView.findViewById(R.id.txt_uni_buy_set_name);
        TextView uniClass = convertView.findViewById(R.id.txt_uni_buy_class);
        TextView uniSchool = convertView.findViewById(R.id.txt_uni_buy_school_name);
        TextView totalBook = convertView.findViewById(R.id.txt_total_uni_buy);
        TextView totalPrize = convertView.findViewById(R.id.txt_uni_buy_total_prize);
        ImageView imgAdd = convertView.findViewById(R.id.img_uniform_add_card_yes);
        ImageView imgRemove = convertView.findViewById(R.id.img_uniform_add_card_no);

        imgAdd.setOnClickListener(view -> {
            databaseReference.child(phone).child("cart").child(arrayList.get(position).getSetName()).setValue(arrayList.get(position));
            imgAdd.setVisibility(View.GONE);
            imgRemove.setVisibility(View.VISIBLE);
        });

        imgRemove.setOnClickListener(view -> {
            databaseReference.child(phone).child("cart").child(arrayList.get(position).getSetName()).removeValue();
            imgAdd.setVisibility(View.VISIBLE);
            imgRemove.setVisibility(View.GONE);
        });

        uniSetName.setText(arrayList.get(position).getSetName());
        uniClass.setText(arrayList.get(position).getuClass());
        uniSchool.setText(arrayList.get(position).getSclName());
        totalBook.setText(String.valueOf(arrayList.get(position).getTotalItem()));
        totalPrize.setText(String.valueOf(arrayList.get(position).getTotalPrize()));


        return convertView;
    }

    public void filterList(ArrayList<UniformSell> s)
    {
        arrayList = s ;
        notifyDataSetChanged();
    }
}
