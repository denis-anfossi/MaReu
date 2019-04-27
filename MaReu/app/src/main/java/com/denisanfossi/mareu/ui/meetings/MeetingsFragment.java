package com.denisanfossi.mareu.ui.meetings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.ui.meeting_creation.MeetingCreationDialogFragment;
import com.denisanfossi.mareu.ui.meeting_creation.MeetingCreationDialogPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;


public class MeetingsFragment extends Fragment implements MeetingsContract.View {

    @BindView(R.id.fragment_meetings_recycler_view) RecyclerView mRecyclerView;
    private FloatingActionButton mCreateMeetingFloatingActionButton;

    private MeetingsContract.Presenter mPresenter;
    private MeetingsAdapter mMeetingsAdapter;

    public MeetingsFragment() {
    }

    public static MeetingsFragment newInstance() {
        return new MeetingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings, container, false);
        ButterKnife.bind(this, view);

        mMeetingsAdapter = new MeetingsAdapter(new ArrayList<>(), new MeetingsAdapter.MeetingsClickListener() {
            @Override
            public void deleteImageButtonOnClick(View v, int position) {
                mPresenter.deleteMeeting(position);
            }
        });
        mRecyclerView.setAdapter(mMeetingsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCreateMeetingFloatingActionButton = getActivity().findViewById(R.id.activity_meetings_add_meeting_fab);
        mCreateMeetingFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.createMeeting();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull MeetingsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showMeetings(List<Meeting> meetings) {
        mMeetingsAdapter.updateData(meetings);
    }

    @Override
    public void showNoMeetings() {
        mMeetingsAdapter.updateData(new ArrayList<>());
    }

    @Override
    public void showMeetingDetails(Meeting meeting) {
        // TODO
    }

    @Override
    public void launchMeetingCreationDialogFragment() {
        MeetingCreationDialogFragment meetingCreationFragment = MeetingCreationDialogFragment.display(getFragmentManager());
        new MeetingCreationDialogPresenter(meetingCreationFragment);
    }
}
