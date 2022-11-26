package com.jaypandit.bookapp.vendor;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookVendorAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BookVendor> vendorArrayList;

    public BookVendorAdapter(Context mContext, ArrayList<BookVendor> vendorArrayList) {
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

        convertView = View.inflate(mContext, R.layout.book_vendor_item_list,null);

        TextView fullName = convertView.findViewById(R.id.txt_book_ven_name);
        TextView shopName = convertView.findViewById(R.id.txt_book_ven_shop_name);
        TextView address = convertView.findViewById(R.id.txt_book_ven_address);
        TextView city = convertView.findViewById(R.id.txt_book_ven_city);
        TextView phoneNo = convertView.findViewById(R.id.txt_book_ven_phone_no);
        TextView permanentNo = convertView.findViewById(R.id.txt_book_ven_permanent_no);
        Spinner spinnerSchoolList = convertView.findViewById(R.id.spinner_book_scl_name);
        Spinner spinnerBookList = convertView.findViewById(R.id.spinner_book_name_list);

        fullName.setText(vendorArrayList.get(position).getVendorFullName());
        shopName.setText(vendorArrayList.get(position).getShopName());
        address.setText(vendorArrayList.get(position).getShopAddress());
        city.setText(vendorArrayList.get(position).getCityName());
        phoneNo.setText(vendorArrayList.get(position).getPhoneNumber());
        permanentNo.setText(vendorArrayList.get(position).getPersonalNumber());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,vendorArrayList.get(position).getSchoolNameList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchoolList.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,vendorArrayList.get(position).getBookNameList());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookList.setAdapter(adapter1);

        return convertView;
    }
}
