package com.denisanfossi.mareu.service;

import com.denisanfossi.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingsApiServiceImpl implements MeetingsApiService {
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
