package com.example.wanghong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wanghong.database.CrimeDBschema.CrimeTable;

/**
 * Created by wanghong on 2016/6/2.
 */
public class MyDataHelper extends SQLiteOpenHelper {
    private static  final  String DBNAME="CriminalIntent.db";
    private  static  final  int VERSION=1;
    public MyDataHelper(Context context) {
        super(context, DBNAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table"+" "+CrimeTable.TABLE_NAME+"("+"id integer primary key autoincrement,"
        +CrimeTable.col.UUID+","+CrimeTable.col.TITLE+","+CrimeTable.col.DATE+","+CrimeTable.col.SOLVED+","+CrimeTable.col.SUSPECT+")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
