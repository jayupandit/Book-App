package com.jaypandit.bookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CollegeDatabase extends SQLiteOpenHelper {

    private static final String DB_Name = "college_db";
    private static final int DB_Version = 1;
    Context mContext;

    public CollegeDatabase(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
        mContext = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table collegeDetail(college_name text(30),board text(30),book text(30),area text(30),branch text(30),state text(30),country text(30))";
        String query2 = "create table schoolDetail(school_name text(30),school_board text(30),school_book text(30),school_area text(30),school_branch text(30),school_state text(30),school_country text(30))";
        db.execSQL(query);
        db.execSQL(query2);

    }

    public void insertCollegeDetail(College cd){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("college_name",cd.getCollegeName());
        cv.put("board",cd.getBoard());
        cv.put("book",cd.getBook());
        cv.put("area",cd.getArea());
        cv.put("branch",cd.getBranch());
        cv.put("state",cd.getState());
        cv.put("country",cd.getCountry());

        long row = database.insert("collegeDetail",null,cv);
        Toast.makeText(mContext, row+"", Toast.LENGTH_SHORT).show();

    }

    public void insertSchoolDetail(School sd){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("school_name",sd.getSchoolName());
        cv.put("school_board",sd.getSchoolBoard());
        cv.put("school_book",sd.getSchoolBook());
        cv.put("school_area",sd.getSchoolArea());
        cv.put("school_branch",sd.getSchoolBranch());
        cv.put("school_state",sd.getSchoolState());
        cv.put("school_country",sd.getSchoolCountry());

        long row = database.insert("schoolDetail",null,cv);
        Toast.makeText(mContext, row+"", Toast.LENGTH_SHORT).show();
    }



    public ArrayList<School> fetchSchoolDetail(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<School> cd = new ArrayList<>();

        Cursor c = db.query("schoolDetail",null,null,null,null,null,null,null);
        c.moveToFirst();

        while (!c.isAfterLast()){

            String name = c.getString(0);
            int board = c.getInt(1);
            int book = c.getInt(2);
            String area = c.getString(3);
            String branch = c.getString(4);
            String state = c.getString(5);
            String country = c.getString(6);


            School detail = new School();
            detail.setSchoolName(name);
            detail.setSchoolBoard(board);
            detail.setSchoolBook(book);
            detail.setSchoolArea(area);
            detail.setSchoolBranch(branch);
            detail.setSchoolState(state);
            detail.setSchoolCountry(country);

            cd.add(detail);

            c.moveToNext();
        }

        return cd;
    }

    public ArrayList<College> fetchCollegeDetail(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<College> cd = new ArrayList<>();

        Cursor c = db.query("collegeDetail",null,null,null,null,null,null,null);
        c.moveToFirst();

        while (!c.isAfterLast()){

            String name = c.getString(0);
            int board = c.getInt(1);
            int book = c.getInt(2);
            String area = c.getString(3);
            String branch = c.getString(4);
            String state = c.getString(5);
            String country = c.getString(6);


            College detail = new College();
            detail.setCollegeName(name);
            detail.setBoard(board);
            detail.setBook(book);
            detail.setArea(area);
            detail.setBranch(branch);
            detail.setState(state);
            detail.setCountry(country);

            cd.add(detail);

            c.moveToNext();
        }

        return cd;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
