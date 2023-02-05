package com.jaypandit.bookapp.master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.vendor.BookVendor;

import java.util.ArrayList;

public class NewBookRecycleAdapter extends RecyclerView.Adapter<NewBookRecycleAdapter.NewBookHolder> {

    Context mContext;
    ArrayList<BookVendor> vendorArrayList;

    public NewBookRecycleAdapter(Context mContext, ArrayList<BookVendor> vendorArrayList) {
        this.mContext = mContext;
        this.vendorArrayList = vendorArrayList;
    }

    @NonNull
    @Override
    public NewBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.book_vendor_item_list,null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new NewBookHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewBookHolder holder, int position) {

        holder.fullName.setText(vendorArrayList.get(position).getVendorFullName());
        holder.shopName.setText(vendorArrayList.get(position).getShopName());
        holder.address.setText(vendorArrayList.get(position).getShopAddress());
        holder.city.setText(vendorArrayList.get(position).getCityName());
        holder.phoneNo.setText(vendorArrayList.get(position).getPhoneNumber());
        holder.permanentNo.setText(vendorArrayList.get(position).getPersonalNumber());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,vendorArrayList.get(position).getSchoolNameList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerSchoolList.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return vendorArrayList.size();
    }

    public class NewBookHolder extends RecyclerView.ViewHolder{

        TextView fullName, shopName, address, city, phoneNo, permanentNo;
        Spinner spinnerSchoolList;

        public NewBookHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.txt_book_ven_name);
            shopName = itemView.findViewById(R.id.txt_book_ven_shop_name);
            address = itemView.findViewById(R.id.txt_book_ven_address);
            city = itemView.findViewById(R.id.txt_book_ven_city);
            phoneNo = itemView.findViewById(R.id.txt_book_ven_phone_no);
            permanentNo = itemView.findViewById(R.id.txt_book_ven_permanent_no);
            spinnerSchoolList = itemView.findViewById(R.id.spinner_book_scl_name);
        }
    }
}
