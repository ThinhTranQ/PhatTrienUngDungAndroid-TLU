package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Question;
import com.example.myapplication.QuizContract;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "quiz.db";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public void addAccount(String user,String username,String password){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name",user);
        values.put("Username",username);
        values.put("Password",password);
        db.insert("ACCOUNT",null,values);
        db.close();

    }
    public boolean query_account(String Username){
            db = this.getWritableDatabase();
            Cursor row_query  = db.rawQuery("SELECT * FROM ACCOUNT WHERE " +
                    "USERNAME = '"+Username+"'",null);
            int count = row_query.getCount();
            row_query.close();
            db.close();
        return count <= 0;
    }
    public boolean check_account_exist(String Username,String password){
        db = this.getWritableDatabase();
        Cursor row_query = db.rawQuery("SELECT * FROM ACCOUNT WHERE USERNAME = '"+ Username +"' AND PASSWORD = '"+password+"'",null);
        int count = row_query.getCount();
        row_query.close();        db.close();
        return count > 0;

    }
    public boolean queryUpdateAccount(String Username,String password){
        //String [] whereArgs = {Username};
        db = this.getWritableDatabase();
        ContentValues update_password = new ContentValues();
        update_password.put("Password",password);
        db.update("Account",update_password,"Username =?",new String[] {Username});
        db.close();
        return true;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
    }
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME , null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
    Cursor readAllData(){
        String query = "SELECT * FROM account order by score desc limit 10";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean queryUpdateAccount_score(String Username,int new_score){
        db = this.getWritableDatabase();
        ContentValues update_score = new ContentValues();
        update_score.put("score",new_score);
        db.update("Account ",update_score," Username =? and score < " + new_score,new String[] {Username});
        db.close();
        return true;
    }
    Cursor readOneData(String username){
        String query = "SELECT * FROM account where username = '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor getRankUser(int _score){
        String query = "SELECT count(*) FROM account where score = " +  _score;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
