package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private  ArrayList user_id, user_name,user_score;

    public CustomAdapter(Context context, ArrayList user_id, ArrayList user_name, ArrayList user_score) {
        this.context = context;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_score = user_score;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user_id.setText(String.valueOf(user_id.get(position)));
        holder.user_name.setText(String.valueOf(user_name.get(position)));
        holder.user_score.setText(String.valueOf(user_score.get(position)));

    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_id, user_name, user_score;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id =  itemView.findViewById(R.id.user_id);
            user_name = itemView.findViewById(R.id.user_name);
            user_score = itemView.findViewById(R.id.user_score);

        }
    }
}
