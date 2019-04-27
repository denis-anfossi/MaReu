package com.denisanfossi.mareu.ui.meeting_creation;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.ui.meetings.MeetingsActivity;
import com.denisanfossi.mareu.utils.DateUtils;
import com.denisanfossi.mareu.utils.pickers.DatePickerFragment;
import com.denisanfossi.mareu.utils.pickers.TimePickerFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingCreationDialogFragment extends DialogFragment implements MeetingCreationDialogContract.View {

    private static final String TAG = "meetingCreationDialogFragment";
    @BindView(R.id.fragment_meeting_creation_dialog_toolbar) Toolbar mToolbar;
    @BindView(R.id.fragment_meeting_creation_topic_text_input) TextInputLayout mTopicTextInputLayout;
    @BindView(R.id.fragment_meeting_creation_venue_text_input) TextInputLayout mVenueTextInputLayout;
    @BindView(R.id.fragment_meeting_creation_date_text_input) TextInputLayout mDateTextInputLayout;
    @BindView(R.id.fragment_meeting_creation_date_input) TextInputEditText mDateTextInputEditText;
    @BindView(R.id.fragment_meeting_creation_date_btn) MaterialButton mDateButton;
    private MeetingCreationDialogContract.Presenter mPresenter;

    public MeetingCreationDialogFragment() {
    }

    public static MeetingCreationDialogFragment display(FragmentManager fragmentManager) {
        MeetingCreationDialogFragment meetingCreationDialogFragment = newInstance();
        meetingCreationDialogFragment.show(fragmentManager, TAG);
        return meetingCreationDialogFragment;
    }

    public static MeetingCreationDialogFragment newInstance() {
        return new MeetingCreationDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_meeting_creation_dialog, container, false);
        ButterKnife.bind(this, view);

        mDateButton.setOnClickListener(v -> mPresenter.setMeetingDate());
        mDateTextInputEditText.setOnClickListener(v -> mPresenter.setMeetingDate());
        mDateTextInputLayout.setOnClickListener(v -> mPresenter.setMeetingDate());
        mDateTextInputEditText.setText(DateUtils.convertDateForPickersDisplay(new Date()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationOnClickListener(v -> dismiss());
        mToolbar.inflateMenu(R.menu.meeting_creation_dialog);
        mToolbar.setOnMenuItemClickListener(item -> {
            mPresenter.createMeeting(mTopicTextInputLayout.getEditText().getText().toString(), mVenueTextInputLayout.getEditText().getText().toString());
            return true;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        ((MeetingsActivity) getContext()).updateMeetingsFragments();
    }

    @Override
    public void showMeetings() {
        dismiss();
    }


    @Override
    public void launchDatePickerDialog(Calendar calendar) {
        DatePickerFragment datePickerFragment = DatePickerFragment.display(getFragmentManager());
        datePickerFragment.setCalendar(calendar);
        datePickerFragment.setOnDateSetListener((view, year, monthOfYear, dayOfMonth) -> mPresenter.saveMeetingDate(year, monthOfYear, dayOfMonth));
    }

    @Override
    public void launchTimePickerDialog(Calendar calendar) {
        TimePickerFragment timePickerFragment = TimePickerFragment.display(getFragmentManager());
        timePickerFragment.setCalendar(calendar);
        timePickerFragment.setOnTimeSetListener((view, hourOfDay, minute) -> mPresenter.saveMeetingTime(hourOfDay, minute));
    }

    @Override
    public void updateMeetingCreationDialogFragment(Date meetingDate) {
        mDateTextInputEditText.setText(DateUtils.convertDateForPickersDisplay(meetingDate));
    }

    @Override
    public void setPresenter(@NonNull MeetingCreationDialogContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
