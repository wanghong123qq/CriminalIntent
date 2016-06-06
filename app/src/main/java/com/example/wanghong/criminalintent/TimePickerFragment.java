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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {
    private static final String TIME_EXTURA = ".timepickerfragment";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Time time) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TIME_EXTURA,time);
        TimePickerFragment timePickerFragment=new TimePickerFragment();
        timePickerFragment.setArguments(bundle);
        return timePickerFragment;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker,null);
        Time mytime= (Time) getArguments().getSerializable(TIME_EXTURA);
        mTimePicker= (TimePicker) view.findViewById(R.id.timepickerid);
        return  new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Time of Crime")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour=mTimePicker.getCurrentHour();
                        int min=mTimePicker.getCurrentMinute();
                        Time t=new Time(hour,min);
                        SendResult(Activity.RESULT_OK,t);


                    }
                }).create();

    }

    private void SendResult(int resultcode,Time m) {
        if(getTargetFragment()==null)
        {
            return;
        }
        Intent i=new Intent();
        i.putExtra(CrimeFragment.GetTIMe,m);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultcode,i);




    }
}
