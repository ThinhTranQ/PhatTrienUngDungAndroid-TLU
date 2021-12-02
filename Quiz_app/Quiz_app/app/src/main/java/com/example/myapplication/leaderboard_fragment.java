package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class leaderboard_fragment extends Fragment {
    RecyclerView recyclerView;
    int rank_ =1;
    DBHandler db;
    ArrayList<String> user_name, user_score;
    ArrayList<Integer> user_id;
    CustomAdapter customAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_leaderboard,container,false);

        recyclerView =view.findViewById(R.id.recyclerview);
        db= new DBHandler(getActivity());
        user_id = new ArrayList<>();
        user_name =  new ArrayList<>();
        user_score = new ArrayList<>();

        storeDataInArrays();
        customAdapter= new CustomAdapter(getActivity(),user_id,user_name,user_score);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    void storeDataInArrays(){
        Cursor cursor =  db.readAllData();
        if(cursor.getCount()==0){
            //Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                user_id.add(rank_);
                user_name.add(cursor.getString(1));
                user_score.add(cursor.getString(4));
                rank_++;
            }
        }

    }
}
