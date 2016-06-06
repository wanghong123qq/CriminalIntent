package com.example.wanghong.criminalintent;

import java.io.Serializable;

/**
 * Created by wanghong on 2016/6/1.
 */
public class Time implements Serializable {
    private  int hour;
    private  int min;
    public Time(int h,int m)

    {
        this.hour=h;
         this.min=m;




    }

    @Override
    public String toString() {

        return hour+":"+min;
    }
}
