package com.denisanfossi.mareu.ui.meetings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.di.DI;
import com.denisanfossi.mareu.service.MeetingsApiService;

public class MeetingsActivity extends AppCompatActivity {
    private MeetingsFragment mMeetingsFragment;
    private MeetingsPresenter mMeetingsPresenter;
    private MeetingsApiService mMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        mMeetings = DI.getNewInstanceMeetingsApiService();

        configureAndShowMeetingsFragment();
        mMeetingsPresenter = new MeetingsPresenter(mMeetingsFragment);
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
