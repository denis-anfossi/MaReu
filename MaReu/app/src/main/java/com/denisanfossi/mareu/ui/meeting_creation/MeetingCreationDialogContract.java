package com.denisanfossi.mareu.ui.meeting_creation;

import com.denisanfossi.mareu.BaseMvp;

import java.util.Calendar;
import java.util.Date;

public interface MeetingCreationDialogContract {

    interface View extends BaseMvp.BaseView<Presenter> {

        void showMeetings();

        void launchDatePickerDialog(Calendar calendar);

        void launchTimePickerDialog(Calendar calendar);

        void updateMeetingCreationDialogFragment(Date meetingDate);
    }

    interface Presenter extends BaseMvp.BasePresenter {

        void createMeeting(String topic, String meetingDateTextInput, String venue);

        void setMeetingDate(String meetingDateTextInput);

        void saveMeetingDate(int year, int month, int dayOfMonth);

        void saveMeetingTime(int hourOfDay, int minute);
    }
}
