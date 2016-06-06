package com.example.wanghong.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wanghong on 2016/5/28.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity{
    protected  abstract Fragment CreateFragment();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.Containerfragmentid);
        if(fragment==null)
        {
           fragment=CreateFragment();
           fm.beginTransaction().add(R.id.Containerfragmentid,fragment).commit();


        }

    }
}

