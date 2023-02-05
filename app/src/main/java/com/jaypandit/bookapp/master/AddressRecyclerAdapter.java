package com.jaypandit.bookapp.master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.AddressHolder> {

    Context mContext;
    ArrayList<Address> mArrayList;
    RecyclerViewClickListener listener;

    public AddressRecyclerAdapter(Context mContext, ArrayList<Address> mArrayList, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.iteam_address_cart,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        AddressHolder holder = new AddressHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {

        holder.txtFullName.setText(mArrayList.get(position).getFullName());
        holder.txtAddress.setText(mArrayList.get(position).getHouseNo() + ", " +mArrayList.get(position).getColony() + ", " + mArrayList.get(position).getPinCode() + ", " + mArrayList.get(position).getCity());
        holder.txtPhoneNo.setText(mArrayList.get(position).getPhoneNumber());


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtFullName, txtAddress, txtPhoneNo, txtEdit;
        RadioButton radioButton;
        Button btnDeliver;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);

            txtFullName = itemView.findViewById(R.id.txt_full_name);
            txtAddress = itemView.findViewById(R.id.txt_address);
            txtPhoneNo = itemView.findViewById(R.id.txt_phone_no);
            txtEdit = itemView.findViewById(R.id.txt_edit);
            radioButton = itemView.findViewById(R.id.radio_btn);
            btnDeliver = itemView.findViewById(R.id.btn_deliver);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
