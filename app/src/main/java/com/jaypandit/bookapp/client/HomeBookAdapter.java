package com.jaypandit.bookapp.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.book.Book;

import java.util.ArrayList;

public class HomeBookAdapter extends RecyclerView.Adapter<HomeBookAdapter.HomeBookHolder> {

    private Context mContext;
    private ArrayList<Book> mList;

    public HomeBookAdapter(Context mContext, ArrayList<Book> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HomeBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.book_item_list,null);
        HomeBookHolder holder = new HomeBookHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBookHolder holder, int position) {

        holder.bookName.setText(mList.get(position).getBookName());
        holder.bookPublisher.setText(mList.get(position).getBookPublisher());
        holder.bookAuthor.setText(mList.get(position).getBookAuthor());
        holder.bookPublishYear.setText(mList.get(position).getBookPublishYear());
        holder.bookBookRup.setText(mList.get(position).getBookPrize());
        holder.bookLanguage.setText(mList.get(position).getBookLanguage());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeBookHolder extends RecyclerView.ViewHolder
    {

        TextView bookName;
        TextView bookPublisher;
        TextView bookPublishYear;
        TextView bookAuthor;
        TextView bookBookRup;
        TextView bookLanguage;

        public HomeBookHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.txt_book_name);
            bookPublisher = itemView.findViewById(R.id.txt_book_publisher);
            bookPublishYear = itemView.findViewById(R.id.txt_book_publish_year);
            bookAuthor = itemView.findViewById(R.id.txt_book_author);
            bookBookRup = itemView.findViewById(R.id.txt_book_rup);
            bookLanguage = itemView.findViewById(R.id.txt_book_language);
        }
    }
}
