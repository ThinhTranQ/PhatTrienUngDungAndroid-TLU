package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login_Fragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_Fragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirstFragmentListener activityCallback;
    public interface FirstFragmentListener {
        public void onButtonClick(int fontsize, String text);
    }
    public void onAttach(Context context){
        super.onAttach(context);
    }
    public Login_Fragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_Fragments.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_Fragments newInstance(String param1, String param2) {
        Login_Fragments fragment = new Login_Fragments();
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
    Button activity_sign_in_active;
    EditText email,password;
    TextView activity_forgot_password;
    DBHandler dbHandler;
    TabLayout tabLayout;
    CheckBox checkBox;
    float v = 0;
    ProgressBar progressBar;
    public static final  String MyPREFERENCES = "MyPre";
    public static final  String EMAIL = "email";
    public static final  String PASSWORD = "password";
    public static final String CHECKED = "checked";
    SharedPreferences sharedPreferences;
    String email_str = "";
    String password_str = "";
    GoogleSignInClient mGoogleSignInClient;
    FloatingActionButton google_1;
    int RC_SIGN_IN = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup login = (ViewGroup) inflater.inflate(R.layout.fragment_login__fragments, container, false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        google_1 = login.findViewById(R.id.sign_in_button);
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        tabLayout = getActivity().findViewById(R.id.tab_layout);
        activity_sign_in_active = login.findViewById(R.id.sign_in);
        activity_forgot_password = login.findViewById(R.id.forgot_password);
        dbHandler = new DBHandler(getActivity());
        email = (EditText) login.findViewById(R.id.email);
        password = (EditText) login.findViewById(R.id.password);
        checkBox = login.findViewById(R.id.checkBox);
        //a = getActivity().findViewById(R.id.forgot_password);
        progressBar = login.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        String username_share = sharedPreferences.getString(EMAIL,"");
        String password_share = sharedPreferences.getString(PASSWORD,"");
        String checked_share = sharedPreferences.getString(CHECKED,"");
        hide_view();
        google_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        if(!username_share.isEmpty() || !password_share.isEmpty()){
            email.setText(username_share, TextView.BufferType.EDITABLE);
            password.setText(password_share,TextView.BufferType.EDITABLE);
            if (checked_share != ""){
                checkBox.setChecked(true);
            }
        }
        if(check_network_connect()){
            Toast.makeText(getActivity(), "No internet connected", Toast.LENGTH_SHORT).show();
        }
        else{
            activity_sign_in_active.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sprite threeBounce = new ThreeBounce();
                    progressBar.setIndeterminateDrawable(threeBounce);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            email_str = email.getText().toString();
                            password_str = password.getText().toString();
                            boolean account_exist = dbHandler.check_account_exist(email_str, password_str);
                            if (!account_exist || email_str.isEmpty() || password_str.isEmpty()) {
                                Toast.makeText(getActivity(), "Thông tin tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor share = sharedPreferences.edit();
                                if (checkBox.isChecked()){
                                    share.putString(EMAIL,email_str);
                                    share.putString(PASSWORD,password_str);
                                    share.putString(CHECKED,"0");
                                    share.commit();
                                }
                                else{
                                    share.clear();
                                    share.commit();
                                }
                                Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),Empty.class);
                                intent.putExtra("email",email_str);
                                startActivity(intent);
                            }
                        }
                    },2000);
                }
            });
            activity_forgot_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ForgotPassword forgotPassword = new ForgotPassword();
                    // fragmentTransaction = getFragmentManager().beginTransaction();
                    //fragmentTransaction.replace(R.id.container,forgotPassword).commit();
                    Sprite threeBounce = new ThreeBounce();
                    progressBar.setIndeterminateDrawable(threeBounce);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getActivity(),check_email_forgot_password.class);
                            startActivity(intent);
                        }
                    },1000);
                }
            });
        }
            // Inflate the layout for this fragment
        return login;
    }
    public void animation_login(){

        google_1.setTranslationX(300);
        google_1.setAlpha(v);
        google_1.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        checkBox.setTranslationX(300);
        checkBox.setAlpha(v);
        checkBox.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        activity_sign_in_active.setTranslationX(300);
        activity_sign_in_active.setAlpha(v);
        activity_sign_in_active.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        activity_forgot_password.setTranslationX(300);
        activity_forgot_password.setAlpha(v);
        activity_forgot_password.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        email.setTranslationX(300);
        email.setAlpha(v);
        email.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        password.setTranslationX(300);
        password.setAlpha(v);
        password.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(0).start();
        show_view();
    }
    public void hide_view(){
        google_1.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);
        activity_sign_in_active.setVisibility(View.GONE);
        activity_forgot_password.setVisibility(View.GONE);
        activity_forgot_password.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
    }
    public void show_view(){
        google_1.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);
        activity_sign_in_active.setVisibility(View.VISIBLE);
        activity_forgot_password.setVisibility(View.VISIBLE);
        activity_forgot_password.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    /*
    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);

        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/
    public boolean check_network_connect(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                ||connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
        )
            return false;
        else
            return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent = new Intent(getActivity(),Empty.class);
            // Signed in successfully, show authenticated UI.
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ERROR", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onResume(){
        animation_login();
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
        hide_view();
        super.onDestroyView();
    }
    @Override
    public void onDestroy(){
        ;super.onDestroy();
    }
}