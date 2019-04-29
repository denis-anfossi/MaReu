package com.denisanfossi.mareu.ui.meetings;

import androidx.annotation.NonNull;

import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingsPresenter implements MeetingsContract.Presenter {

    private final MeetingsContract.View mView;
    private List<Meeting> mMeetings;

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
        mMeetings = DI.getMeetingsApiService().getMeetings();
        if (mMeetings.isEmpty()) {
            mView.showNoMeetings();
        } else {
            Collections.sort(mMeetings);
            mView.showMeetings(mMeetings);
        }
    }

    @Override
    public void createMeeting() {
        mView.launchMeetingCreationDialogFragment();
    }

    @Override
    public void deleteMeeting(int position) {
        DI.getMeetingsApiService().deleteMeeting(mMeetings.get(position));
        loadMeetings();
    }

}
