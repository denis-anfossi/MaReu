package com.denisanfossi.mareu.ui.meetings;

import androidx.annotation.NonNull;

import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingsPresenter implements MeetingsContract.Presenter {

    private final MeetingsContract.View mView;

    public MeetingsPresenter(@NonNull MeetingsContract.View view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMeetings();
    }

    @Override
    public void loadMeetings() {
        List<Meeting> meetings = DI.getMeetingsApiService().getMeetings();
        if (meetings.isEmpty()) {
            mView.showNoMeetings();
        } else {
            Collections.sort(meetings);
            mView.showMeetings(meetings);
        }
    }

    @Override
    public void createMeeting() {
        mView.launchMeetingCreationDialogFragment();
    }

    @Override
    public void deleteMeeting(int position) {
        List<Meeting> meetings = DI.getMeetingsApiService().getMeetings();
        checkNotNull(meetings);
        meetings.remove(position);
        loadMeetings();
    }

}
