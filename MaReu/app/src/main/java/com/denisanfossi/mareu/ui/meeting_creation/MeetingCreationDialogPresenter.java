package com.denisanfossi.mareu.ui.meeting_creation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;
import com.denisanfossi.mareu.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingCreationDialogPresenter implements MeetingCreationDialogContract.Presenter {

    private MeetingCreationDialogContract.View mView;
    private Date mMeetingDate;
    private Calendar mCalendar;

    public MeetingCreationDialogPresenter(@NonNull MeetingCreationDialogContract.View view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void createMeeting(String topic, String meetingDateTextInput, String venue) {
        mMeetingDate = DateUtils.convertStringToDateForPickersDisplay(meetingDateTextInput);
        Meeting meeting = new Meeting(topic, mMeetingDate, venue);
        DI.getMeetingsApiService().addMeeting(meeting);
        mView.showMeetings();
    }

    @Override
    public void setMeetingDate(String meetingDateTextInput) {
        mMeetingDate = DateUtils.convertStringToDateForPickersDisplay(meetingDateTextInput);
        mCalendar.setTime(mMeetingDate);
        mView.launchDatePickerDialog(mCalendar);
    }

    @Override
    public void saveMeetingDate(int year, int monthOfYear, int dayOfMonth) {
        Log.d("Test", "Date: " + year + "/" + monthOfYear + "/" + dayOfMonth);
        mCalendar.set(year, monthOfYear, dayOfMonth);
        mMeetingDate = mCalendar.getTime();
        mView.updateMeetingCreationDialogFragment(mMeetingDate);
        mView.launchTimePickerDialog(mCalendar);
    }

    @Override
    public void saveMeetingTime(int hourOfDay, int minute) {
        Log.d("Test", "Time: " + hourOfDay + ":" + minute);
        mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
        mMeetingDate = mCalendar.getTime();
        mView.updateMeetingCreationDialogFragment(mMeetingDate);
    }

    @Override
    public void start() {
        mCalendar = Calendar.getInstance();
        mMeetingDate = new Date();
    }
}
