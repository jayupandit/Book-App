package com.jaypandit.bookapp.master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookCartRecycleAdapter extends RecyclerView.Adapter<BookCartRecycleAdapter.BookCartHolder> {

    Context mContext;
    ArrayList<BookSell> bookSellArrayList;

    public BookCartRecycleAdapter(Context mContext, ArrayList<BookSell> bookSellArrayList) {
        this.mContext = mContext;
        this.bookSellArrayList = bookSellArrayList;
    }

    @NonNull
    @Override
    public BookCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.book_cart_item_list,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new BookCartHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCartHolder holder, int position) {

        String rupee = mContext.getResources().getString(R.string.RS);
        holder.txtSetName.setText(bookSellArrayList.get(position).getBookSetName());
        holder.txtClassName.setText(bookSellArrayList.get(position).getClassName());
        holder.txtBoard.setText(bookSellArrayList.get(position).getBoard());
        holder.txtPrice.setText(rupee + " " + String.valueOf(bookSellArrayList.get(position).getTotal()));
        holder.txtTotalItem.setText(String.valueOf(bookSellArrayList.get(position).getBookNo()));
    }

    @Override
    public int getItemCount() {
        return bookSellArrayList.size();
    }

    public class BookCartHolder extends RecyclerView.ViewHolder {

        TextView txtSetName, txtClassName, txtBoard, txtPrice, txtTotalItem;

        public BookCartHolder(@NonNull View itemView) {
            super(itemView);

            txtSetName = itemView.findViewById(R.id.txt_book_cart_set_name);
            txtClassName = itemView.findViewById(R.id.txt_book_cart_class);
            txtBoard = itemView.findViewById(R.id.txt_total_book_cart);
            txtPrice = itemView.findViewById(R.id.txt_book_cart_price);
            txtTotalItem = itemView.findViewById(R.id.txt_book_cart_total_item);
        }
    }
}
