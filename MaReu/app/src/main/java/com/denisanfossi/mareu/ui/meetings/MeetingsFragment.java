package com.denisanfossi.mareu.ui.meetings;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.ui.meeting_creation.MeetingCreationDialogFragment;
import com.denisanfossi.mareu.ui.meeting_creation.MeetingCreationDialogPresenter;
import com.denisanfossi.mareu.utils.ConstantsValues;
import com.denisanfossi.mareu.utils.pickers.DatePickerFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;


public class MeetingsFragment extends Fragment implements MeetingsContract.View {

    private static final String TAG = "meetingsFragment";
    @BindView(R.id.fragment_meetings_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_meetings_card_view_filter) CardView mFilterCardView;
    @BindView(R.id.fragment_meetings_card_view_filter_expand_btn) ImageButton mFilterExpandButton;
    @BindView(R.id.fragment_meetings_card_view_filter_collapse_btn) ImageButton mFilterCollapseButton;
    @BindView(R.id.fragment_meetings_card_view_filter_apply_btn) MaterialButton mFilterApplyButton;
    @BindView(R.id.fragment_meetings_card_view_filter_venue_layout) TextInputLayout mFilterVenueTextInputLayout;
    @BindView(R.id.fragment_meetings_card_view_filter_begin_date_layout) TextInputLayout mFilterBeginDateTextInputLayout;
    @BindView(R.id.fragment_meetings_card_view_filter_end_date_layout) TextInputLayout mFilterEndDateTextInputLayout;
    @BindDrawable(R.drawable.ic_venue_24dp) Drawable mVenueIconDrawable;
    @BindDrawable(R.drawable.ic_date_time_24dp) Drawable mDateTimeIconDrawable;
    @BindDrawable(R.drawable.ic_expand_more_24dp) Drawable mExpandIconDrawable;
    @BindDrawable(R.drawable.ic_clear_24dp) Drawable mClearIconDrawable;
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

        mMeetingsAdapter = new MeetingsAdapter(new ArrayList<>(), (v, position) -> mPresenter.deleteMeeting(position));
        mRecyclerView.setAdapter(mMeetingsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCreateMeetingFloatingActionButton = getActivity().findViewById(R.id.activity_meetings_add_meeting_fab);
        mCreateMeetingFloatingActionButton.setOnClickListener(v -> mPresenter.createMeeting());

        configureFilterCardView();
        return view;
    }

    private void configureFilterCardView() {
        mFilterCardView.setOnClickListener(v -> mPresenter.loadOrUnloadFilters(mFilterVenueTextInputLayout.getEditText().getText().toString(),
                mFilterBeginDateTextInputLayout.getEditText().getText().toString(),
                mFilterEndDateTextInputLayout.getEditText().getText().toString(), true));
        mFilterExpandButton.setOnClickListener(v -> mPresenter.loadOrUnloadFilters(mFilterVenueTextInputLayout.getEditText().getText().toString(),
                mFilterBeginDateTextInputLayout.getEditText().getText().toString(),
                mFilterEndDateTextInputLayout.getEditText().getText().toString(), true));
        mFilterCollapseButton.setOnClickListener(v -> mPresenter.loadOrUnloadFilters(mFilterVenueTextInputLayout.getEditText().getText().toString(),
                mFilterBeginDateTextInputLayout.getEditText().getText().toString(),
                mFilterEndDateTextInputLayout.getEditText().getText().toString(), true));
        mFilterApplyButton.setOnClickListener(v -> mPresenter.loadOrUnloadFilters(mFilterVenueTextInputLayout.getEditText().getText().toString(),
                mFilterBeginDateTextInputLayout.getEditText().getText().toString(),
                mFilterEndDateTextInputLayout.getEditText().getText().toString(), false));
        configureVenueTextInputLayout();
        configureBeginDateTextInputLayout();
        configureEndDateTextInputLayout();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureVenueTextInputLayout() {
        mFilterVenueTextInputLayout.getEditText().setOnFocusChangeListener((v, hasFocus) -> mPresenter.saveFilterVenue(mFilterVenueTextInputLayout.getEditText().getText().toString()));
        mFilterVenueTextInputLayout.getEditText().setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP &&
                        mFilterVenueTextInputLayout.getEditText().getCompoundDrawablesRelative()[DRAWABLE_RIGHT] != null &&
                        mFilterVenueTextInputLayout.getEditText().getCompoundDrawablesRelative()[DRAWABLE_RIGHT].equals(mClearIconDrawable)) {
                    if (event.getRawX() >= (mFilterVenueTextInputLayout.getEditText().getRight() - mFilterVenueTextInputLayout.getEditText().getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        mFilterVenueTextInputLayout.getEditText().setText("");
                        mPresenter.saveFilterVenue("");
                        return true;
                    }
                }
                return false;
            }
        });
        mFilterVenueTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mFilterVenueTextInputLayout.getEditText().getText().length() > 0) {
                    mFilterVenueTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mVenueIconDrawable, null, mClearIconDrawable, null);
                } else {
                    mFilterVenueTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mVenueIconDrawable, null, null, null);
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureBeginDateTextInputLayout() {
        mFilterBeginDateTextInputLayout.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mFilterBeginDateTextInputLayout.setErrorEnabled(false);
                mPresenter.setFilterBeginDate(mFilterBeginDateTextInputLayout.getEditText().getText().toString());
            } else {
                mPresenter.setFilterBeginDateManual(mFilterBeginDateTextInputLayout.getEditText().getText().toString());
            }
        });
        mFilterBeginDateTextInputLayout.getEditText().setOnClickListener(v -> mPresenter.setFilterBeginDate(mFilterBeginDateTextInputLayout.getEditText().getText().toString()));
        mFilterBeginDateTextInputLayout.getEditText().setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mFilterBeginDateTextInputLayout.getEditText().getCompoundDrawablesRelative()[DRAWABLE_RIGHT].equals(mClearIconDrawable)) {
                    if (event.getRawX() >= (mFilterBeginDateTextInputLayout.getEditText().getRight() - mFilterBeginDateTextInputLayout.getEditText().getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        mFilterBeginDateTextInputLayout.getEditText().setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        mFilterBeginDateTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mFilterBeginDateTextInputLayout.getEditText().getText().length() > 0) {
                    mFilterBeginDateTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mDateTimeIconDrawable, null, mClearIconDrawable, null);
                } else {
                    mFilterBeginDateTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mDateTimeIconDrawable, null, mExpandIconDrawable, null);
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureEndDateTextInputLayout() {
        mFilterEndDateTextInputLayout.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mFilterEndDateTextInputLayout.setErrorEnabled(false);
                mPresenter.setFilterEndDate(mFilterEndDateTextInputLayout.getEditText().getText().toString());
            } else {
                mPresenter.setFilterEndDateManual(mFilterEndDateTextInputLayout.getEditText().getText().toString());
            }
        });
        mFilterEndDateTextInputLayout.getEditText().setOnClickListener(v -> mPresenter.setFilterEndDate(mFilterEndDateTextInputLayout.getEditText().getText().toString()));
        mFilterEndDateTextInputLayout.getEditText().setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mFilterEndDateTextInputLayout.getEditText().getCompoundDrawablesRelative()[DRAWABLE_RIGHT].equals(mClearIconDrawable)) {
                    if (event.getRawX() >= (mFilterEndDateTextInputLayout.getEditText().getRight() - mFilterEndDateTextInputLayout.getEditText().getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        mFilterEndDateTextInputLayout.getEditText().setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        mFilterEndDateTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mFilterEndDateTextInputLayout.getEditText().getText().length() > 0) {
                    mFilterEndDateTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mDateTimeIconDrawable, null, mClearIconDrawable, null);
                } else {
                    mFilterEndDateTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mDateTimeIconDrawable, null, mExpandIconDrawable, null);
                }
            }
        });
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
    public void launchMeetingCreationDialogFragment() {
        MeetingCreationDialogFragment meetingCreationFragment = MeetingCreationDialogFragment.display(getFragmentManager());
        new MeetingCreationDialogPresenter(meetingCreationFragment);
    }

    @Override
    public void expandOrCollapseFilterCardView() {
        if (mFilterExpandButton.getVisibility() == View.VISIBLE) {
            mFilterExpandButton.setVisibility(View.GONE);
            mFilterVenueTextInputLayout.setVisibility(View.VISIBLE);
            mFilterBeginDateTextInputLayout.setVisibility(View.VISIBLE);
            mFilterEndDateTextInputLayout.setVisibility(View.VISIBLE);
            mFilterApplyButton.setVisibility(View.VISIBLE);
            mFilterCollapseButton.setVisibility(View.VISIBLE);
        } else {
            mFilterCollapseButton.setVisibility(View.GONE);
            mFilterApplyButton.setVisibility(View.GONE);
            mFilterEndDateTextInputLayout.setVisibility(View.GONE);
            mFilterBeginDateTextInputLayout.setVisibility(View.GONE);
            mFilterVenueTextInputLayout.setVisibility(View.GONE);
            mFilterExpandButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateFiltersTextInputLayout(String filterVenue, String filterBeginDate, String filterEndDate) {
        mFilterVenueTextInputLayout.getEditText().setText(filterVenue);
        mFilterBeginDateTextInputLayout.getEditText().setText(filterBeginDate);
        mFilterEndDateTextInputLayout.getEditText().setText(filterEndDate);
        if (filterVenue.isEmpty())
            mFilterVenueTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mVenueIconDrawable, null, null, null);
        else
            mFilterVenueTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mVenueIconDrawable, null, mClearIconDrawable, null);
        mFilterBeginDateTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mDateTimeIconDrawable, null, mExpandIconDrawable, null);
        mFilterEndDateTextInputLayout.getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(mDateTimeIconDrawable, null, mExpandIconDrawable, null);
        mFilterVenueTextInputLayout.getEditText().setSelection(mFilterVenueTextInputLayout.getEditText().getText().length());
        mFilterBeginDateTextInputLayout.getEditText().setSelection(mFilterBeginDateTextInputLayout.getEditText().getText().length());
        mFilterEndDateTextInputLayout.getEditText().setSelection(mFilterEndDateTextInputLayout.getEditText().getText().length());
    }

    @Override
    public void setErrorFilterBeginDate() {
        mFilterBeginDateTextInputLayout.setError("Change canceled - Expected: dd/mm/yy or empty");
    }

    @Override
    public void setErrorFilterEndDate() {
        mFilterEndDateTextInputLayout.setError("Change canceled - Expected: dd/mm/yy or empty");
    }

    @Override
    public void launchDatePickerDialog(Calendar calendar, boolean beginOrEnd) {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
        datePickerFragment.setCalendar(calendar);
        if (beginOrEnd)
            datePickerFragment.setOnDateSetListener((view, year, monthOfYear, dayOfMonth) -> mPresenter.saveFilterDate(year, monthOfYear, dayOfMonth, ConstantsValues.BEGIN_DATE));
        else
            datePickerFragment.setOnDateSetListener((view, year, monthOfYear, dayOfMonth) -> mPresenter.saveFilterDate(year, monthOfYear, dayOfMonth, ConstantsValues.END_DATE));
        datePickerFragment.show(getFragmentManager(), DatePickerFragment.getTAG());
    }
}
