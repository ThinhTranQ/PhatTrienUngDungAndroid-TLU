package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_Up extends AppCompatActivity {
    Button sign_up_up,back_activity;
    DBHandler dbHandler;
    EditText name_text;
    EditText password_text;
    EditText email_text;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign__up);
        dbHandler = new DBHandler(this);
        context = this;
        name_text = findViewById(R.id.username);
        email_text = findViewById(R.id.email_up);
        password_text = findViewById(R.id.password_up);
        sign_up_up = findViewById(R.id.sign_up_2);
        sign_up_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                check_sign_up();
            }
        });
        back_activity = findViewById(R.id.back);
        back_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void check_sign_up(){
        String name = name_text.getText().toString();
        String email = email_text.getText().toString();
        String password = password_text.getText().toString();
        boolean status = dbHandler.query_account(email);
        if(!isEmailValid(email)){
            Toast.makeText(context, "Email kh??ng h???p l??? vui l??ng nh???p ????ng ?????a ch??? email", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6){
            Toast.makeText(context, "M???t kh???u qu?? ng???n", Toast.LENGTH_SHORT).show();
        }
        else if(password.contains(" ")){
            Toast.makeText(context, "M???t kh???u kh??ng ???????c ch???a kho???ng tr???ng", Toast.LENGTH_SHORT).show();
        }
        else{
            if (status) {
                dbHandler.addAccount(name, email, password);
                Toast.makeText(context, "????ng k?? t??i kho???n th??nh c??ng" + email, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(context, "T??i kho???n n??y ???? ???????c ????ng k?? " + email, Toast.LENGTH_LONG).show();
            }
        }
    }
}