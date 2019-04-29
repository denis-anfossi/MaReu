package com.denisanfossi.mareu.utils.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import static com.google.common.base.Preconditions.checkNotNull;

public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "datePickerFragment";
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private Calendar mCalendar;

    public DatePickerFragment() {
    }

    public static DatePickerFragment newInstance() {
        return new DatePickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static String getTAG() {
        return TAG;
    }

    public void setOnDateSetListener(@NonNull DatePickerDialog.OnDateSetListener onDateSetListener) {
        mOnDateSetListener = checkNotNull(onDateSetListener);
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), mOnDateSetListener, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
        return datePickerDialog;
    }
}
