package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class reset_password extends AppCompatActivity {
    EditText old_pass;
    EditText new_pass;
    EditText confirm_pass;
    Button reset_password,back_2;
    Context context;
    DBHandler DB;
    ProgressBar progressBar_3;
    TextView textView;
    Intent intent_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        DB = new DBHandler(this);
        old_pass =  findViewById(R.id.password_old);
        new_pass = findViewById(R.id.passwrod_new);
        confirm_pass = findViewById(R.id.password_confirm);
        reset_password = findViewById(R.id.reset_password);
        context = this;
        intent_data = getIntent();
        progressBar_3 = findViewById(R.id.progressBar3);
        textView = findViewById(R.id.textView_error);
        progressBar_3.setVisibility(View.GONE);
        reset_password.setOnClickListener(v -> {
            String old_pass_str = old_pass.getText().toString();
            String new_pass_str = new_pass.getText().toString();
            String confirm_str = confirm_pass.getText().toString();
            String email_reset = intent_data.getStringExtra("email");
            Toast.makeText(context, email_reset+"\n"+old_pass_str, Toast.LENGTH_SHORT).show();
            boolean check = DB.check_account_exist(email_reset,old_pass_str);
            if (!check){
                textView.setText("Mật khẩu cũ không khớp");
                textView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.GONE);
                    }
                },3000);
            }
            else if(old_pass_str.equals(new_pass_str)){
                textView.setText("Mật khẩu mới không được trùng với mật khẩu cũ");
                textView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.GONE);
                    }
                },3000);
            }
            else {
                if(new_pass_str.contains(" ") || old_pass_str.contains(" ") || confirm_str.contains(" ")){
                    textView.setText("Mật khẩu không được chứa khoảng trắng");
                    textView.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textView.setVisibility(View.GONE);
                        }
                    },3000);
                }
                else if(new_pass_str.length() < 6){
                    textView.setText("Mật khẩu quá ngắn");
                    textView.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textView.setVisibility(View.GONE);
                        }
                    },3000);
                }
                else if(!new_pass_str.equals(confirm_str)){
                    Toast.makeText(this,"Mật khẩu không khớp",Toast.LENGTH_LONG).show();
                }
                else if(new_pass_str.equals(confirm_str)){
                    Sprite threeBounce = new ThreeBounce();
                    progressBar_3.setIndeterminateDrawable(threeBounce);
                    progressBar_3.setVisibility(View.VISIBLE);
                    boolean update = DB.queryUpdateAccount(email_reset,confirm_str);
                    if(update){
                        Toast.makeText(context,"Cập nhật mật khẩu thành công",Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar_3.setVisibility(View.GONE);
                                Intent intent = new Intent(context,MainActivity.class);
                                startActivity(intent);
                            }
                        },3000);
                    }
                }
            }
        });
    }
}