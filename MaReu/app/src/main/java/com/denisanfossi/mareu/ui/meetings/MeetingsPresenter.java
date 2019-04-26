package com.denisanfossi.mareu.ui.meetings;

import androidx.annotation.NonNull;

import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingsPresenter implements MeetingsContract.Presenter {

    private final MeetingsContract.View mMeetingsView;

    public MeetingsPresenter(@NonNull MeetingsContract.View meetingsView) {
        mMeetingsView = checkNotNull(meetingsView);
        mMeetingsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMeetings();
    }

    @Override
    public void loadMeetings() {
        List<Meeting> meetings = DI.getMeetingsApiService().getMeetings();
        if (meetings.isEmpty()) {
            mMeetingsView.showNoMeetings();
        } else {
            mMeetingsView.showMeetings(meetings);
        }
    }

    @Override
    public void createMeeting() {

    }

    @Override
    public void deleteMeeting(int position) {
        List<Meeting> meetings = DI.getMeetingsApiService().getMeetings();
        checkNotNull(meetings);
        meetings.remove(position);
        loadMeetings();
    }

}
