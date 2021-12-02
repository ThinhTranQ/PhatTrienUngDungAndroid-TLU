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

public class check_email_forgot_password extends AppCompatActivity {
    Button continue_1;
    Context context;
    EditText email_1;
    DBHandler dbHandler;
    ProgressBar progressBar_2;
    TextView textView;
    TextView hint_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_email_check);
        dbHandler = new DBHandler(this);
        context = this;
        continue_1 = findViewById(R.id.bt_fg_continue);
        email_1 = findViewById(R.id.forgot_password_email);
        progressBar_2 = findViewById(R.id.progressBar2);
        progressBar_2.setVisibility(View.GONE);
        textView = findViewById(R.id.email_khoi_phuc);
        continue_1.setOnClickListener(v -> {
            String email_str = email_1.getText().toString();
            if (email_str.isEmpty()) {
                textView.setText("Email không được để trống");
                textView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.GONE);
                    }
                },5000);
            }
            else if(!isEmailValid(email_str)){
                textView.setText("Email không tồn tại");
                textView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.GONE);
                    }
                },5000);
            }
            else{
                Sprite threeBounce = new ThreeBounce();
                progressBar_2.setIndeterminateDrawable(threeBounce);
                progressBar_2.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            boolean check_email = dbHandler.query_account(email_str);
                            if(check_email)
                                Toast.makeText(context, "Email ko hợp lệ", Toast.LENGTH_SHORT).show();
                            else
                            {
                                Intent intent = new Intent(context,reset_password.class);
                                intent.putExtra("email",email_str);
                                startActivity(intent);
                            }

                    }
                },2000);
            }
    });
}
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}