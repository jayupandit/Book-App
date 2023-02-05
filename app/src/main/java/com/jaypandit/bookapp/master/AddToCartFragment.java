package com.jaypandit.bookapp.master;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaypandit.bookapp.R;
import com.jaypandit.bookapp.book.Book;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToCartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<BookSell> bookSellArrayList;
    ArrayList<UniformSell> uniformSellArrayList;

    DatabaseReference database;

    public AddToCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddToCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddToCartFragment newInstance(String param1, String param2) {
        AddToCartFragment fragment = new AddToCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_to_cart, container, false);

        ListView listView = viewGroup.findViewById(R.id.cart_list_view_1);

        bookSellArrayList = new ArrayList<>();
        uniformSellArrayList = new ArrayList<>();

        SharedPreferences preferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String phone = preferences.getString("mobile",null);

        database = FirebaseDatabase.getInstance().getReference("User").child(phone).child("cart");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookSellArrayList.clear();
                for (DataSnapshot schoolDataSnap : snapshot.getChildren()){
                    BookSell book = schoolDataSnap.getValue(BookSell.class);
                    bookSellArrayList.add(book);
                }

                BookCartAdapter adapter = new BookCartAdapter(getContext(),bookSellArrayList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return viewGroup;
    }
}