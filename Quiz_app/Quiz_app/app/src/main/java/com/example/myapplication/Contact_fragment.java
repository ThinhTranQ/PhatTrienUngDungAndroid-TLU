package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contact_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Contact_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact_fragment newInstance(String param1, String param2) {
        Contact_fragment fragment = new Contact_fragment();
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
    EditText title_1;
    EditText email_1;
    EditText content_1;
    Button send;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_contact_fragment, container, false);
        title_1 = viewGroup.findViewById(R.id.title_send);
        email_1 = viewGroup.findViewById(R.id.email_send);
        content_1 = viewGroup.findViewById(R.id.content_send);
        send = viewGroup.findViewById(R.id.send_button);
        hide_view();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_1.getText().toString();
                String title = title_1.getText().toString();
                String content = content_1.getText().toString();
                if(isEmailValid(email)){
                    Intent mail = new Intent(Intent.ACTION_SEND);
                    mail.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                    mail.putExtra(Intent.EXTRA_SUBJECT,title);
                    mail.putExtra(Intent.EXTRA_TEXT,content);
                    mail.setType("message/rfc822");
                    startActivity(Intent.createChooser(mail, "Choose an Email client :"));
                }
            }
        });
        // Inflate the layout for this fragment
        return viewGroup ;
    }
    public void hide_view(){
        title_1.setVisibility(View.GONE);
        email_1.setVisibility(View.GONE);
        content_1.setVisibility(View.GONE);
        send.setVisibility(View.GONE);
    }
    public void show_view(){
        title_1.setVisibility(View.VISIBLE);
        email_1.setVisibility(View.VISIBLE);
        content_1.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
    }
    public void animation(){
        title_1.setTranslationX(300);
        title_1.setAlpha(0);
        title_1.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        email_1.setTranslationX(300);
        email_1.setAlpha(0);
        email_1.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        content_1.setTranslationX(300);
        content_1.setAlpha(0);
        content_1.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        send.setTranslationX(300);
        send.setAlpha(0);
        send.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        show_view();
    }
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Override
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
        hide_view();
        super.onPause();
    }
    @Override
    public void onStop(){
        hide_view();
        super.onStop();
    }
    @Override
    public void onDestroyView(){

        hide_view();super.onDestroyView();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}