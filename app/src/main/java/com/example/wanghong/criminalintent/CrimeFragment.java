package com.example.wanghong.criminalintent;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment {
    public  static  final String EXTRUA="com.examole.wanghong.criminalintent.crimefragemnt";
    private  static  final  String DATEFRAGMENT="date";
    private  static  final  String TIMEFRAGMENT="time";
    private  static  final  int  REQUEST_DATE=0;
    private  static  final int Time_REQUEST=1;
    public  static final String GETEXTURA="GETSENDRESULT";
    public  static final String GetTIMe="getime";
    private static  final int REQUEST_contact=2;

    private  Crime mCrime;
    private EditText mEditText;
    private Button mButton, timebutton,contactbutton,sendcrimebutton;
    private CheckBox mCheckBox;
    public static CrimeFragment newInstance(UUID uuid)//fragment之间传数据.通过制定id生成fragment
    { Bundle bundle=new Bundle();
        bundle.putSerializable(EXTRUA,uuid);
        CrimeFragment crimeFragment=new CrimeFragment();
        crimeFragment.setArguments(bundle);

        return  crimeFragment;



    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID muuid= (UUID) getArguments().getSerializable(EXTRUA);
        mCrime=CrimeLab.getCrimeLab(getActivity()).getCrime(muuid);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crime,container,false);
        mEditText= (EditText) v.findViewById(R.id.Edtextid);
        mEditText.setText(mCrime.getMtitle());
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  mCrime.setMtitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mButton= (Button) v.findViewById(R.id.datebuttonid);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                DatePickerFragment datePickerFragment=DatePickerFragment.newInstance(mCrime.getMdate());
                datePickerFragment.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                datePickerFragment.show(fragmentManager,DATEFRAGMENT);
            }
        });

        mButton.setText(Crime.FormatDate(mCrime.getMdate()));
        timebutton= (Button) v.findViewById(R.id.timebuttonid);
        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                TimePickerFragment timePickerFragment=TimePickerFragment.newInstance(mCrime.getTime());
                timePickerFragment.setTargetFragment(CrimeFragment.this,Time_REQUEST);
                timePickerFragment.show(fm, TIMEFRAGMENT);
            }
        });
        timebutton.setText(mCrime.getTime().toString());
        mCheckBox= (CheckBox) v.findViewById(R.id.checkid);
        mCheckBox.setChecked(mCrime.isMsloved());
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setMsloved(isChecked);
            }
        });
        contactbutton= (Button) v.findViewById(R.id.Contactid);
        contactbutton.setText(R.string.GetContact);
        contactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i,REQUEST_contact);


            }
        });
        if(mCrime.getMsuspect()!=null)
        {
            contactbutton.setText(mCrime.getMsuspect());
        }
        sendcrimebutton= (Button) v.findViewById(R.id.sendCrimeid);
        sendcrimebutton.setText(R.string.Send_Crime);
        sendcrimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,getreport());
                intent.putExtra(Intent.EXTRA_TITLE,R.string.Send_Crime);
               intent= Intent.createChooser(intent,getString(R.string.Send_Crime));
                startActivity(intent);
            }
        });
        return  v;


    }
    private  String getreport()
    {

        String solvedstring=null;
        if(mCrime.isMsloved())
        {
            solvedstring=getString(R.string.Crime_solved);


        }
        else { solvedstring=getString(R.string.Crime_nosolved);}
        String date=Crime.FormatDate(mCrime.getMdate());
        String suspect=mCrime.getMsuspect();
        if(suspect==null)
        {

           suspect=getString(R.string.Crime_nosuspect);

        }
        else
        {suspect=getString(R.string.Crime_suspect,suspect);}
        String report=getString(R.string.Crime_report,mCrime.getMtitle(),date,solvedstring,suspect);
        return  report;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK)
        {
            return;
        }
        switch (requestCode)
        {
            case REQUEST_DATE: Date date= (Date) data.getSerializableExtra(GETEXTURA);
                mCrime.setMdate(date);
                mButton.setText(Crime.FormatDate(mCrime.getMdate()));
                break;

        case Time_REQUEST:
             Time time= (Time) data.getSerializableExtra(GetTIMe);
             mCrime.setTime(time);
             timebutton.setText(mCrime.getTime().toString());
            break;
            case REQUEST_contact:
                Uri  uri=data.getData();
                String [] strings=new String[]{ ContactsContract.Contacts.DISPLAY_NAME};
                Cursor cursor=getActivity().getContentResolver().query(uri,strings,null,null,null);
                try {
                    if(cursor.getCount()==0)
                    {
                        return;
                    }
                    cursor.moveToFirst();
                    String mysuspect=cursor.getString(0);
                    mCrime.setMsuspect(mysuspect);
                    contactbutton.setText(mysuspect);

                } finally {
                    cursor.close();

                }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_crimeid:
                CrimeLab crimeLab=CrimeLab.getCrimeLab(getActivity());
                crimeLab.DeleteCrime(mCrime);
                Intent i=new Intent(getActivity(),CrimeListActivity.class);
                startActivity(i);



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crime_menu,menu);

    }

 @Override
 public void onPause() {
    super.onPause();
     CrimeLab.getCrimeLab(getActivity()).updata(mCrime);
}
}
