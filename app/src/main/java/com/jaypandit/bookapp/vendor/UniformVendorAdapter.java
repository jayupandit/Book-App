package com.jaypandit.bookapp.vendor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class UniformVendorAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<UniformVendor> vendorArrayList;

    public UniformVendorAdapter(Context mContext, ArrayList<UniformVendor> vendorArrayList) {
        this.mContext = mContext;
        this.vendorArrayList = vendorArrayList;
    }

    @Override
    public int getCount() {
        return vendorArrayList.size();
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

        convertView = View.inflate(mContext, R.layout.uniform_vendor_iteam_list,null);

        TextView vendorName = convertView.findViewById(R.id.txt_uni_vendor_name);
        TextView shopName = convertView.findViewById(R.id.txt_uni_ven_shop_name);
        TextView address = convertView.findViewById(R.id.txt_uni_ven_address);
        TextView city = convertView.findViewById(R.id.txt_uni_ven_city);
        TextView phoneNo = convertView.findViewById(R.id.txt_uni_ven_phone_no);
        TextView permanentNo = convertView.findViewById(R.id.txt_uni_ven_permanent_no);
        Spinner spinnerSchoolNameList = convertView.findViewById(R.id.spinner_uni_school_name);
        Spinner spinnerShirtColor = convertView.findViewById(R.id.spinner_uni_shirt_color);
        Spinner spinnerPantColor = convertView.findViewById(R.id.spinner_uni_pant_color);

        vendorName.setText(vendorArrayList.get(position).getFullName());
        shopName.setText(vendorArrayList.get(position).getShopName());
        address.setText(vendorArrayList.get(position).getShopAddress());
        city.setText(vendorArrayList.get(position).getShopCity());
        phoneNo.setText(vendorArrayList.get(position).getShopPhoneNo());
        permanentNo.setText(vendorArrayList.get(position).getShopPermanentNo());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,vendorArrayList.get(position).getSchoolNameList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchoolNameList.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,vendorArrayList.get(position).getShirtColorList());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShirtColor.setAdapter(adapter1);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,vendorArrayList.get(position).getPantColorList());
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPantColor.setAdapter(adapter3);


        return convertView;
    }
}
