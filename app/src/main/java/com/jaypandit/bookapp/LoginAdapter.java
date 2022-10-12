package com.jaypandit.bookapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LoginAdapter extends FragmentStateAdapter {

    String[] titles = new String[]{"Login","SignUp"};
    Context mContext;

    public LoginAdapter(Context context) {
        super((FragmentActivity) context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SignupTabFragment signUpTabFragment = new SignupTabFragment(mContext);
                return signUpTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
