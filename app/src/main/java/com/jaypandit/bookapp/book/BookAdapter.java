package com.jaypandit.bookapp.book;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jaypandit.bookapp.R;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Book> bookArrayList;

    public BookAdapter(Context context,ArrayList<Book> arrayList) {
        mContext = context ;
        bookArrayList = arrayList ;
    }

    @Override
    public int getCount() {
        return bookArrayList.size();
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

        view = View.inflate(mContext, R.layout.book_item_list,null);

        TextView bookName = view.findViewById(R.id.txt_book_name);
        TextView bookPublisher = view.findViewById(R.id.txt_book_publisher);
        TextView bookPublishYear = view.findViewById(R.id.txt_book_publish_year);
        TextView bookAuthor = view.findViewById(R.id.txt_book_author);
        TextView bookBookRup = view.findViewById(R.id.txt_book_rup);
        TextView bookLanguage = view.findViewById(R.id.txt_book_language);

//        Toast.makeText(mContext, bookArrayList.get(i).getClasss()+"", Toast.LENGTH_SHORT).show();

        bookName.setText(bookArrayList.get(i).getBookName());
        bookPublisher.setText(bookArrayList.get(i).getBookPublisher());
        bookAuthor.setText(bookArrayList.get(i).getBookAuthor());
        bookPublishYear.setText(bookArrayList.get(i).getBookPublishYear());
        bookBookRup.setText(bookArrayList.get(i).getBookPrize());
        bookLanguage.setText(bookArrayList.get(i).getBookLanguage());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,BookListEditActivity.class);
                intent.putExtra("Name",bookArrayList.get(i).getBookName());
                intent.putExtra("Publisher",bookArrayList.get(i).getBookPublisher());
                intent.putExtra("Author",bookArrayList.get(i).getBookAuthor());
                intent.putExtra("PublishYear",bookArrayList.get(i).getBookPublishYear());
                intent.putExtra("Language",bookArrayList.get(i).getBookLanguage());
                intent.putExtra("Prize",bookArrayList.get(i).getBookPrize());
                mContext.startActivity(intent);


            }
        });

        return view;
    }

    public void filterList(ArrayList<Book> s)
    {
        bookArrayList = s ;
        notifyDataSetChanged();
    }
}
