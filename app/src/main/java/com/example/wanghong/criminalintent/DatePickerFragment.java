package com.example.wanghong.criminalintent;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {
    public static final String EXTURA_DATE = "com.example.wanghong.criminalintent.dailogframent";
    private Date mDate;
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTURA_DATE, date);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date) getArguments().getSerializable(EXTURA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.datepickerid);
        mDatePicker.init(year, month, day, null);
        return new AlertDialog.Builder(getActivity()).setTitle("Date of Crime")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       int year=mDatePicker.getYear();
                        int mounth=mDatePicker.getMonth();
                        int day=mDatePicker.getDayOfMonth();
                        Date mdate=new GregorianCalendar(year,mounth,day).getTime();
                        SendResult(Activity.RESULT_OK,mdate);

                    }
                }).create();

    }

    public void SendResult(int result, Date date)//用来回传给CrimeFragments数据

    {
        if (getTargetFragment() == null) {
            return;

        }
        Intent intent = new Intent();
        intent.putExtra(CrimeFragment.GETEXTURA, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), result, intent);
        Log.i("SEND","result");


    }
}
