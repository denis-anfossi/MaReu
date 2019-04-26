package com.denisanfossi.mareu.service;

import com.denisanfossi.mareu.data.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingsApiService implements MeetingsApiService {
    private List<Meeting> mMeetingsList = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetingsList;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetingsList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetingsList.remove(meeting);
    }
}
