package com.example.wanghong.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager= (ViewPager) findViewById(R.id.viewpagerid);
        UUID uuid= (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRUA);//获取intent额外的数据
        FragmentManager fm=getSupportFragmentManager();
        mCrimeArrayList=CrimeLab.getCrimeLab(getApplicationContext()).getArrayList();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime=mCrimeArrayList.get(position);
                CrimeFragment crimeFragment=CrimeFragment.newInstance(crime.getMuuid());
                return crimeFragment;



            }

            @Override
            public int getCount() {
                return mCrimeArrayList.size();
            }
        });
       for(int i=0;i<mCrimeArrayList.size();i++)
       {
           if(mCrimeArrayList.get(i).getMuuid().equals(uuid))

           {
               mViewPager.setCurrentItem(i);
               break;
           }

       }

    }
}
