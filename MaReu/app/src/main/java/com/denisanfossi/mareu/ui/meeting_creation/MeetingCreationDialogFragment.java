package com.denisanfossi.mareu.ui.meeting_creation;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.ui.meetings.MeetingsActivity;
import com.denisanfossi.mareu.utils.DateUtils;
import com.denisanfossi.mareu.utils.pickers.DatePickerFragment;
import com.denisanfossi.mareu.utils.pickers.TimePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingCreationDialogFragment extends DialogFragment implements MeetingCreationDialogContract.View {

    private static final String TAG = "meetingCreationDialogFragment";

    @BindView(R.id.fragment_meeting_creation_dialog_toolbar) Toolbar mToolbar;
    @BindView(R.id.fragment_meeting_creation_topic_text_input) TextInputLayout mTopicTextInputLayout;
    @BindView(R.id.fragment_meeting_creation_venue_text_input) TextInputLayout mVenueTextInputLayout;
    @BindView(R.id.fragment_meeting_creation_date_text_input_layout) TextInputLayout mDateTextInputLayout;
    @BindView(R.id.fragment_meetings_creation_participants_card_view) CardView mParticipantsCardView;
    @BindView(R.id.fragment_meeting_creation_participants_list_text) TextView mParticipantsListText;

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
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_meeting_creation_dialog, container, false);
        ButterKnife.bind(this, view);
        configureTopicTextInputLayout();
        configureVenueTextInputLayout();
        configureDateTextInputLayout();
        configureParticipantsCardView();
        return view;
    }

    private void configureTopicTextInputLayout() {
        mTopicTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mTopicTextInputLayout.getEditText().getText().length() > 0) {
                    mTopicTextInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    private void configureVenueTextInputLayout() {
        mVenueTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mVenueTextInputLayout.getEditText().getText().length() > 0) {
                    mVenueTextInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    private void configureDateTextInputLayout() {
        mDateTextInputLayout.getEditText().setOnClickListener(v -> mPresenter.setMeetingDate(mDateTextInputLayout.getEditText().getText().toString()));
        mDateTextInputLayout.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mDateTextInputLayout.setErrorEnabled(false);
                mPresenter.setMeetingDate(mDateTextInputLayout.getEditText().getText().toString());
            }
        });
        mDateTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mDateTextInputLayout.getEditText().getText().length() > 0) {
                    mDateTextInputLayout.setErrorEnabled(false);
                }
            }
        });
        mDateTextInputLayout.getEditText().setText(DateUtils.convertDateForPickersDisplay(new Date()));
    }

    private void configureParticipantsCardView() {
        mParticipantsCardView.setOnClickListener(v -> mPresenter.addParticipants());
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationOnClickListener(v -> dismiss());
        mToolbar.inflateMenu(R.menu.meeting_creation_dialog);
        mToolbar.setOnMenuItemClickListener(item -> {
            mPresenter.createMeeting(mTopicTextInputLayout.getEditText().getText().toString(), mDateTextInputLayout.getEditText().getText().toString(), mVenueTextInputLayout.getEditText().getText().toString());
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
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
        datePickerFragment.setCalendar(calendar);
        datePickerFragment.setOnDateSetListener((view, year, monthOfYear, dayOfMonth) -> mPresenter.saveMeetingDate(year, monthOfYear, dayOfMonth));
        datePickerFragment.show(getFragmentManager(), DatePickerFragment.getTAG());
    }

    @Override
    public void launchTimePickerDialog(Calendar calendar) {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
        timePickerFragment.setCalendar(calendar);
        timePickerFragment.setOnTimeSetListener((view, hourOfDay, minute) -> mPresenter.saveMeetingTime(hourOfDay, minute));
        timePickerFragment.show(getFragmentManager(), TimePickerFragment.getTAG());
    }

    @Override
    public void launchAddParticipantsDialog(Set<String> actualParticipants) {
        AddParticipantsDialogFragment addParticipantsDialogFragment = AddParticipantsDialogFragment.display(getFragmentManager());
        addParticipantsDialogFragment.setParticipants(actualParticipants);
        addParticipantsDialogFragment.setOnParticipantsSetListener(participants -> mPresenter.saveParticipants(participants));
    }

    @Override
    public void updateDateMeetingCreationDialogFragment(Date meetingDate) {
        mDateTextInputLayout.getEditText().setText(DateUtils.convertDateForPickersDisplay(meetingDate));
    }

    @Override
    public void updateParticipantsMeetingCreationDialogFragment(String participants) {
        mParticipantsListText.setText(participants);
        mParticipantsListText.setVisibility(View.VISIBLE);
    }

    @Override
    public void setErrorTopic() {
        mTopicTextInputLayout.setError("Should not be empty!");
    }

    @Override
    public void setErrorVenue() {
        mVenueTextInputLayout.setError("Should not be empty!");
    }

    @Override
    public void setErrorDate() {
        mDateTextInputLayout.setError("Should not be empty!");
    }

    @Override
    public void setErrorDateTime() {
        mDateTextInputLayout.setError("Need a valid date & time!");
    }

    @Override
    public void setPresenter(@NonNull MeetingCreationDialogContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
