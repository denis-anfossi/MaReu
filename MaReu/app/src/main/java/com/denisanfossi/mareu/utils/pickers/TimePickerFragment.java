package com.denisanfossi.mareu.utils.pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

import static com.google.common.base.Preconditions.checkNotNull;

public class TimePickerFragment extends DialogFragment {

    private static final String TAG = "timePickerFragment";
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    private Calendar mCalendar;

    public TimePickerFragment() {

    }

    public static TimePickerFragment display(FragmentManager fragmentManager) {
        TimePickerFragment timePickerFragment = newInstance();
        timePickerFragment.show(fragmentManager, TAG);
        return timePickerFragment;
    }

    public static TimePickerFragment newInstance() {
        return new TimePickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getContext(), mOnTimeSetListener, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
    }

    public void setOnTimeSetListener(@NonNull TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        mOnTimeSetListener = checkNotNull(onTimeSetListener);
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }
}
