package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LoginAdapter extends FragmentStatePagerAdapter {
    public LoginAdapter(@NonNull FragmentManager fm,int behavior) {
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new Sign_up_fragment();
            case 2:
                return new Contact_fragment();
            default:
                return new Login_Fragments();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position){
        String title = "";
        switch (position){
            case 0:
                title = "Sign in";
                break;
            case 1:
                title = "Sign up";
                break;
            case 2:
                title = "Contact me";
                break;
        }
        return title;
    }
}