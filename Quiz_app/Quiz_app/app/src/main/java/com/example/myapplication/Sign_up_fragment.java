package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sign_up_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sign_up_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sign_up_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sign_up_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Sign_up_fragment newInstance(String param1, String param2) {
        Sign_up_fragment fragment = new Sign_up_fragment();
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
    Button sign_up_up,back_activity;
    DBHandler dbHandler;
    EditText name_text;
    EditText password_text;
    EditText email_text;
    TabLayout tabLayout;
    ProgressBar progressBar;
    TextView tv;
    float v=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_fragment, container, false);

        dbHandler = new DBHandler(getActivity());
        tabLayout = getActivity().findViewById(R.id.tab_layout);
        name_text = viewGroup.findViewById(R.id.username);
        email_text = viewGroup.findViewById(R.id.email_up);
        password_text = viewGroup.findViewById(R.id.password_up);
        tv = viewGroup.findViewById(R.id.textView);
        sign_up_up = viewGroup.findViewById(R.id.sign_up_2);
        progressBar = viewGroup.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        hide_view_up();
        sign_up_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    progressBar.setVisibility(View.VISIBLE);
                    Sprite threeBounce = new ThreeBounce();
                    progressBar.setIndeterminateDrawable(threeBounce);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            check_sign_up();
                        }
                    },2000);
                }
        });
        // Inflate the layout for this fragment
        return viewGroup;
    }
    public void animation(){
        name_text.setTranslationX(300);
        name_text.setAlpha(v);
        name_text.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        email_text.setTranslationX(300);
        email_text.setAlpha(v);
        email_text.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        password_text.setTranslationX(300);
        password_text.setAlpha(v);
        password_text.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        tv.setTranslationX(300);
        tv.setAlpha(v);
        tv.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        sign_up_up.setTranslationX(300);
        sign_up_up.setAlpha(v);
        sign_up_up.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        show_view_up();
    }

    public void hide_view_up(){
        name_text.setVisibility(View.GONE);
        email_text.setVisibility(View.GONE);
        password_text.setVisibility(View.GONE);
        sign_up_up.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
    }
    public void show_view_up(){
        name_text.setVisibility(View.VISIBLE);
        email_text.setVisibility(View.VISIBLE);
        password_text.setVisibility(View.VISIBLE);
        sign_up_up.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
    }
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @SuppressLint("SetTextI18n")
    public void check_sign_up(){
        String name = name_text.getText().toString();
        String email = email_text.getText().toString();
        String password = password_text.getText().toString();
        boolean status = dbHandler.query_account(email);
        if(!isEmailValid(email)){
            tv.setText("Email không hợp lệ vui lòng nhập đúng địa chỉ email");
            tv.setTextColor(Color.RED);
            //Toast.makeText(getActivity(), "Email không hợp lệ vui lòng nhập đúng địa chỉ email", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6){
            tv.setText("Mật khẩu quá ngắn");
            tv.setTextColor(Color.RED);
            //Toast.makeText(getActivity(), "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
        }
        else if(password.contains(" ")){
            tv.setText("Mật khẩu không được chứa khoảng trắng");
            tv.setTextColor(Color.RED);
        }
        else if (status) {
            dbHandler.addAccount(name, email, password);
            Toast.makeText(getActivity(), "Đăng kí tài khoản thành công" + email, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Tài khoản này đã được đăng kí " + email, Toast.LENGTH_LONG).show();
        }
    }@Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onResume(){
        animation();
        super.onResume();
    }
    @Override
    public void onPause(){
        hide_view_up();
        super.onPause();
    }
    @Override
    public void onStop(){
        hide_view_up();
        super.onStop();
    }
    @Override
    public void onDestroyView(){

        hide_view_up();super.onDestroyView();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}