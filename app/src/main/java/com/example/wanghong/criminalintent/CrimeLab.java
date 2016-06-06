package com.example.wanghong.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wanghong.database.*;
import com.example.wanghong.database.CrimeDBschema.CrimeTable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by wanghong on 2016/5/27.
 */
public class CrimeLab {//创建单例类用来保存crime类
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private  ArrayList<Crime>mArrayList;

    private CrimeLab(Context context) {
        this.mContext = context.getApplicationContext();
        MyDataHelper myDataHelper = new MyDataHelper(mContext);
        mSQLiteDatabase = myDataHelper.getWritableDatabase();

    }

    public static CrimeLab getCrimeLab(Context context)//得到实例
    {

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());

        }
        return sCrimeLab;

    }

    private static ContentValues getcontentvalues(Crime crime)//数据库操作中用来存储数据信息的
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.col.UUID, crime.getMuuid().toString());
        contentValues.put(CrimeTable.col.TITLE, crime.getMtitle());
        contentValues.put(CrimeTable.col.DATE, crime.getMdate().getTime());
        contentValues.put(CrimeTable.col.SOLVED, crime.isMsloved()?1:0);
        contentValues.put(CrimeTable.col.SUSPECT,crime.getMsuspect());
        return contentValues;


    }

    public void addCrime(Crime crime) {//在数据库中插入数据
        ContentValues contentValues = getcontentvalues(crime);
        mSQLiteDatabase.insert(CrimeTable.TABLE_NAME, null, contentValues);

    }

    public void updata(Crime crime)//当添加一个Crime对象的时候在数据库中更新
    {
        String UUId = crime.getMuuid().toString();
        ContentValues contentValues = getcontentvalues(crime);
        mSQLiteDatabase.update(CrimeTable.TABLE_NAME, contentValues, CrimeTable.col.UUID + "=?", new String[]{UUId});


    }

  private MyCursor quetyCrimes(String where, String[] args)//用来辅助获取数组列表
    {
        Cursor cursor = mSQLiteDatabase.query(CrimeTable.TABLE_NAME, null, where, args, null, null, null);
        return  new MyCursor(cursor);

    }

    public void DeleteCrime(Crime crime) {

    }

    public ArrayList<Crime> getArrayList() {//返回数组列表
        mArrayList=new ArrayList<Crime>();

        MyCursor myCursor=quetyCrimes(null,null);
        try {
            myCursor.moveToFirst();
            while ((!myCursor.isAfterLast()))
            {

              mArrayList.add(myCursor.getCrime());
                myCursor.moveToNext();


            }
        } finally {
            myCursor.close();

        }

          return  mArrayList;
    }

    public Crime getCrime(UUID id) {
       MyCursor cursor = quetyCrimes(
                CrimeTable.col.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }
}
