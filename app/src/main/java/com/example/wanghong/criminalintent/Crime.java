package com.example.wanghong.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wanghong on 2016/5/27.
 */
public class Crime {//定义一个陋习类
    private UUID muuid;
    private Date mdate;
    private Time mTime;
    private String mtitle;
    private  boolean msloved;
    private  String msuspect;

    public String getMsuspect() {
        return msuspect;
    }

    public void setMsuspect(String msuspect) {
        this.msuspect = msuspect;
    }

    public UUID getMuuid() {
        return muuid;
    }

    public Time getTime() {
        return mTime;
    }

    public void setTime(Time time) {
        mTime = time;
    }

    public  Crime()
    {
       this(UUID.randomUUID());

    }
    public  Crime(UUID uuId)
    {
        this.muuid=uuId;
        mdate=new Date();

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mdate);
        mTime=new Time(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));




    }
    public static String FormatDate(Date date)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
         String ss=simpleDateFormat.format(date);
        return ss ;


    }


    public Date getMdate() {
        return mdate;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public boolean isMsloved() {
        return msloved;
    }

    public void setMsloved(boolean msloved) {
        this.msloved = msloved;
    }

    @Override
    public String toString() {
        return mtitle;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }
}
