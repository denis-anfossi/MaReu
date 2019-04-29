package com.denisanfossi.mareu.ui.meetings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;
import com.denisanfossi.mareu.service.MeetingsApiService;

import java.util.Date;

public class MeetingsActivity extends AppCompatActivity {
    private MeetingsFragment mMeetingsFragment;
    private MeetingsPresenter mMeetingsPresenter;
    private MeetingsApiService mMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        mMeetings = DI.getNewInstanceApiService();

        addFakeMeetings();
        configureAndShowMeetingsFragment();
        mMeetingsPresenter = new MeetingsPresenter(mMeetingsFragment);
    }

    private void addFakeMeetings() {
        Meeting meeting;
        meeting = new Meeting("Topic", new Date(), "google");
        meeting.addParticipant("toto@toto.fr");
        meeting.addParticipant("coucou@coucou.com");
        meeting.addParticipant("hello@hello.com");
        meeting.addParticipant("adressemail@adressemail.com");
        mMeetings.addMeeting(meeting);
        meeting = new Meeting("Subject", new Date(), "nexus");
        meeting.addParticipant("toto@toto.fr");
        mMeetings.addMeeting(meeting);
        meeting = new Meeting("Meeting", new Date(), "Nice");
        mMeetings.addMeeting(meeting);
    }

    private void configureAndShowMeetingsFragment() {

        mMeetingsFragment = (MeetingsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_meetings);
        if (mMeetingsFragment == null) {
            mMeetingsFragment = MeetingsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_meetings, mMeetingsFragment)
                    .commit();
        }
    }

    public void updateMeetingsFragments() {
        mMeetingsPresenter.loadMeetings();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
