package com.denisanfossi.mareu.ui.meeting_creation;

import androidx.annotation.NonNull;

import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;
import com.denisanfossi.mareu.utils.DateUtils;
import com.google.common.base.Joiner;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingCreationDialogPresenter implements MeetingCreationDialogContract.Presenter {

    private final MeetingCreationDialogContract.View mView;
    private Date mMeetingDate;
    private Calendar mCalendar;
    private Set<String> mParticipants;

    public MeetingCreationDialogPresenter(@NonNull MeetingCreationDialogContract.View view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void createMeeting(String topic, String meetingDateTextInput, String venue) {
        boolean isError = false;
        if (topic.isEmpty()) {
            isError = true;
            mView.setErrorTopic();
        }
        if (venue.isEmpty()) {
            isError = true;
            mView.setErrorVenue();
        }
        if (meetingDateTextInput.isEmpty()) {
            isError = true;
            mView.setErrorDate();
        } else if ((mMeetingDate = DateUtils.convertStringToDateForSave(meetingDateTextInput)) == null) {
            isError = true;
            mView.setErrorDateTime();
        }
        if (!isError) {
            Meeting meeting = new Meeting(topic, mMeetingDate, venue);
            for (String participant : mParticipants) {
                meeting.addParticipant(participant);
            }
            DI.getMeetingsApiService().addMeeting(meeting);
            mView.showMeetings();
        }
    }

    @Override
    public void setMeetingDate(String meetingDateTextInput) {
        mMeetingDate = DateUtils.convertStringToDateForPickersDisplay(meetingDateTextInput);
        if (mMeetingDate == null) {
            mMeetingDate = new Date();
        }
        mView.updateDateMeetingCreationDialogFragment(mMeetingDate);
        mCalendar.setTime(mMeetingDate);
        mView.launchDatePickerDialog(mCalendar);
    }

    @Override
    public void saveMeetingDate(int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(year, monthOfYear, dayOfMonth);
        mMeetingDate = mCalendar.getTime();
        mView.updateDateMeetingCreationDialogFragment(mMeetingDate);
        mView.launchTimePickerDialog(mCalendar);
    }

    @Override
    public void saveMeetingTime(int hourOfDay, int minute) {
        mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
        mMeetingDate = mCalendar.getTime();
        mView.updateDateMeetingCreationDialogFragment(mMeetingDate);
    }

    @Override
    public void addParticipants() {
        mView.launchAddParticipantsDialog();
    }

    @Override
    public void saveParticipants(Set<String> participants) {
        mParticipants = participants;
        mView.updateParticipantsMeetingCreationDialogFragment(getParticipantsString());
    }

    private String getParticipantsString() {
        return "Participants list:\n\n".concat(Joiner.on("\n").skipNulls().join(mParticipants));
    }

    @Override
    public void start() {
        mCalendar = Calendar.getInstance();
        mMeetingDate = new Date();
        mParticipants = new TreeSet<>();
    }
}
