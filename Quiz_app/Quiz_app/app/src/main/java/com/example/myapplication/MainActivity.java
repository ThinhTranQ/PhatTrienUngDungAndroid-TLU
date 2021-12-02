package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends FragmentActivity{
    TabLayout tabLayout;
    ViewPager viewPager;
    //Button activity_sign_up_active,activity_sign_in_active,a;
    //EditText email,password;
    //TextView activity_forgot_password;
    //DBHandler dbHandler;
    float m = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tabLayout = findViewById(R.id.tab_layout);
            viewPager = findViewById(R.id.view_paper);
            LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTranslationY(0);
            tabLayout.setAlpha(0);
            tabLayout.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
    }
}

    /*
    private void enable_sign_in(){
        String email_str = email.getText().toString();
        String password_str = password.getText().toString();
        activity_sign_in_active.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("") && password_str.equals("")){
                    activity_sign_in_active.setEnabled(false);
                }
                else if(!s.toString().equals("") && !password_str.equals("")){
                    activity_sign_in_active.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }*/
