package com.example.wanghong.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Crime> mCrimeArrayList;
    private MyAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crime_list_item1, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerid);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrimeArrayList = CrimeLab.getCrimeLab(getActivity()).getArrayList();
        myAdapter = new MyAdapter(mCrimeArrayList);
        mRecyclerView.setAdapter(myAdapter);
        return v;
    }

    class MyViewHolder extends RecyclerView.ViewHolder//定义自己的viewholder
    {
        public TextView nameTextView, datetextview;
        public CheckBox mCheckBox;
        private Crime mCrime;

        public MyViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                    intent.putExtra(CrimeFragment.EXTRUA, mCrime.getMuuid());
                    startActivity(intent);
                }
            });
            nameTextView = (TextView) itemView.findViewById(R.id.textnameid);
            datetextview = (TextView) itemView.findViewById(R.id.datenameid);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBoxid);

        }

        public void bind(Crime crime) {
            mCrime = crime;
            nameTextView.setText(mCrime.getMtitle());
            datetextview.setText(Crime.FormatDate(mCrime.getMdate())+" "+mCrime.getTime());
            mCheckBox.setChecked(mCrime.isMsloved());


        }


    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<Crime> mCrimes;

        public MyAdapter(ArrayList<Crime> arrayList) {

            mCrimes = arrayList;


        }

        public void setCrimes(ArrayList<Crime> crimes) {
            mCrimes=crimes;


        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.crime_list_item, null);

            return new MyViewHolder(view);
        }//为每个子item动态加载布局

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {//讲数据和布局绑定在一起
            Crime crime = mCrimes.get(position);
            holder.bind(crime);

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myAdapter.setCrimes(mCrimeArrayList);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.CreateCrimeid:
                Crime mm = new Crime();
                CrimeLab.getCrimeLab(getActivity()).addCrime(mm);
                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                intent.putExtra(CrimeFragment.EXTRUA, mm.getMuuid());
                startActivity(intent);
                return true;
            case R.id.subtitleid:
                ArrayList<Crime> arrayList = CrimeLab.getCrimeLab(getActivity()).getArrayList();
                int count = arrayList.size();
                String subtitle = getString(R.string.subtitleformat, count);
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setTitle(subtitle);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crimefragment_list_menu, menu);
    }
}
