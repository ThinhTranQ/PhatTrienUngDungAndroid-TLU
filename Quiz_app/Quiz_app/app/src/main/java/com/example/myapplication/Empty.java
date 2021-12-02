package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class Empty extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    //private TextView textViewHighscore;
    //private int highscore;
/*
    RecyclerView recyclerView;
    int rank_ =1;
    DBHandler db;
    ArrayList<String> user_name, user_score;
    ArrayList<Integer> user_id;
    CustomAdapter customAdapter;
*/

    //Button buttonStartQuiz;
    DBHandler dbHandler;
    String personName = "";
    String personEmail = "";
    public String  username_login = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Intent intent =getIntent();
        username_login= intent.getStringExtra("email");

        //textViewHighscore = findViewById(R.id.text_view_highscore);
        //loadHighscore();

        //buttonStartQuiz = findViewById(R.id.button_start_quiz);

        dbHandler = new DBHandler(this);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            //String personGivenName = acct.getGivenName();
            //String personFamilyName = acct.getFamilyName();
            personName = acct.getEmail();
            //String personId = acct.getId();
            //Uri personPhoto = acct.getPhotoUrl();
            dbHandler.addAccount(personName,personEmail,null);
        }
        /*buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });*/
/*
        recyclerView =findViewById(R.id.recyclerview);
        db= new DBHandler(Empty.this);
        user_id = new ArrayList<>();
        user_name =  new ArrayList<>();
        user_score = new ArrayList<>();
*//*
        storeDataInArrays();
        customAdapter= new CustomAdapter(Empty.this,user_id,user_name,user_score);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Empty.this));
*/

        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment() ).commit();
    }
    public String getUsername_login() {
        return username_login;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new home_fragment();
                            break;
                        case R.id.nav_leaderboard:
                            selectedFragment = new leaderboard_fragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new profile_fragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
/*
    void storeDataInArrays(){
        Cursor cursor =  db.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                user_id.add(rank_);
                user_name.add(cursor.getString(1));
                if (cursor.getString(4)==null){
                    user_score.add("0");
                }
                user_score.add(cursor.getString(4));
                rank_++;
            }
        }
    }
*/

    private void startQuiz() {
        Intent intent = new Intent(Empty.this, QuizStart.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizStart.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }*/
    /*
    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }*/
}