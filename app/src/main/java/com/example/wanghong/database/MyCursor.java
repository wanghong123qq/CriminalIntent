package com.example.wanghong.database;

import android.database.Cursor;
import android.database.CursorWrapper;
 import com.example.wanghong.criminalintent.*;
import com.example.wanghong.database.CrimeDBschema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wanghong on 2016/6/3.
 */
public class MyCursor extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MyCursor(Cursor cursor) {
        super(cursor);
    }
    public  Crime getCrime()
    {
        String uuid=getString(getColumnIndex(CrimeTable.col.UUID));
        String title=getString(getColumnIndex(CrimeTable.col.TITLE));
        long date=getLong(getColumnIndex(CrimeTable.col.DATE));
        int i=getInt(getColumnIndex(CrimeTable.col.SOLVED));
        String suspect=getString(getColumnIndex(CrimeTable.col.SUSPECT));
        Crime crime=new Crime(UUID.fromString(uuid));
        crime.setMtitle(title);
        crime.setMdate(new Date(date));
        crime.setMsloved((i!=0));
        crime.setMsuspect(suspect);
        return  crime;





    }
}
