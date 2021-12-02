package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class profile_fragment extends Fragment {
    Button btn_logout;
    TextView txt_name,txt_username,txt_rank,txt_score;
    DBHandler db;
    int get_score;
    String get_user_name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view =(ViewGroup)inflater.inflate(R.layout.fragment_profile,container,false);
        Empty activity = (Empty) getActivity();
        String myDataFromEmpty = activity.getUsername_login();
        get_user_name = myDataFromEmpty;


        txt_name = view.findViewById(R.id.txt_name);
        txt_username = view.findViewById(R.id.txt_username);
        txt_rank = view.findViewById(R.id.txt_rank);
        txt_score = view.findViewById(R.id.txt_score);

/*
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logout();
            }
        });
  */      txt_username.setText("Username: "+get_user_name);
        showdata();
        getrank();

        return view;
    }

    private void logout() {
        Intent in = new Intent(getActivity(), Login_Fragments.class);
        startActivity(in);
        //Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show();
    }
    private void showdata() {
        db= new DBHandler(getActivity());
        Cursor cursor=db.readOneData(get_user_name);
        while(cursor.moveToNext()){
            txt_name.setText("name: "+cursor.getString(1));
            txt_score.setText("score: "+cursor.getString(4));
            get_score = cursor.getInt(4);//txt_rank.setText(cursor.getString(1));
        }
    }
    private void getrank(){
        db = new DBHandler(getActivity());
        Cursor cursor = db.getRankUser(get_score);
        while(cursor.moveToNext()){
            txt_rank.setText("rank: " + cursor.getString(0));
        }
    }

}
