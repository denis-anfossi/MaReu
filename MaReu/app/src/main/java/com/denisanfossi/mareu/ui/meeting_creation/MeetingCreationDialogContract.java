package com.denisanfossi.mareu.ui.meeting_creation;

import com.denisanfossi.mareu.BaseMvp;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public interface MeetingCreationDialogContract {

    interface View extends BaseMvp.BaseView<Presenter> {

        void showMeetings();

        void launchDatePickerDialog(Calendar calendar);

        void launchTimePickerDialog(Calendar calendar);

        void launchAddParticipantsDialog();

        void updateDateMeetingCreationDialogFragment(Date meetingDate);

        void updateParticipantsMeetingCreationDialogFragment(String participants);

        void setErrorTopic();

        void setErrorVenue();

        void setErrorDate();

        void setErrorDateTime();
    }

    interface Presenter extends BaseMvp.BasePresenter {

        void createMeeting(String topic, String meetingDateTextInput, String venue);

        void setMeetingDate(String meetingDateTextInput);

        void saveMeetingDate(int year, int month, int dayOfMonth);

        void saveMeetingTime(int hourOfDay, int minute);

        void addParticipants();

        void saveParticipants(Set<String> participants);
    }
}
