package com.denisanfossi.mareu.ui.meetings;

import androidx.annotation.NonNull;

import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;
import com.denisanfossi.mareu.utils.ConstantsValues;
import com.denisanfossi.mareu.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MeetingsPresenter implements MeetingsContract.Presenter {

    private static final String TAG = "meetingsPresenter";
    private final MeetingsContract.View mView;
    private List<Meeting> mMeetings;
    private Date mFilterBeginDate;
    private Date mFilterEndDate;
    private String mFilterVenue;

    public MeetingsPresenter(@NonNull MeetingsContract.View view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
        mFilterBeginDate = DateTimeUtils.setTimeToDate(null, 0, 0, 0, false);
        mFilterEndDate = DateTimeUtils.setTimeToDate(null, 23, 59, 59, true);
        mFilterVenue = "";
    }

    @Override
    public void start() {
        loadMeetings();
    }

    @Override
    public void loadMeetings() {
        mMeetings = new ArrayList<>(DI.getMeetingsApiService().getMeetings());
        if (mMeetings.isEmpty()) {
            mView.showNoMeetings();
        } else {
            Collections.sort(mMeetings);
            filterDate();
            filterVenue();
            mView.showMeetings(mMeetings);
        }
        mView.updateFiltersTextInputLayout(mFilterVenue, DateTimeUtils.getStringForDateFilterDisplay(mFilterBeginDate), DateTimeUtils.getStringForDateFilterDisplay(mFilterEndDate));
    }

    private void filterVenue() {
        Iterator<Meeting> meetingIterator = mMeetings.iterator();

        while (meetingIterator.hasNext()) {
            if (!meetingIterator.next().getVenue().toLowerCase().startsWith(mFilterVenue.toLowerCase())) {
                meetingIterator.remove();
            }
        }
    }

    private void filterDate() {
        Iterator<Meeting> meetingIterator = mMeetings.iterator();
        while (meetingIterator.hasNext()) {
            Meeting meeting = meetingIterator.next();
            if ((mFilterBeginDate.getTime() != -3600000 && meeting.getDate().compareTo(mFilterBeginDate) < 0) ||
                    (mFilterEndDate.getTime() != -3600000 && meeting.getDate().compareTo(mFilterEndDate) > 0)) {
                meetingIterator.remove();
            }
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

    @Override
    public void loadOrUnloadFilters(String filterVenue, String filterBeginDate, String filterEndDate, boolean expandOrCollapse) {
        boolean isError = false;
        if (filterBeginDate.isEmpty()) {
            mFilterBeginDate.setTime(-3600000);
        } else if (DateTimeUtils.convertStringToDateForSave(filterBeginDate) == null) {
            isError = true;
            mView.setErrorFilterBeginDate();
        } else {
            mFilterBeginDate = DateTimeUtils.setTimeToDate(DateTimeUtils.convertStringToDateForSave(filterBeginDate), 0, 0, 0, false);
        }
        if (filterEndDate.isEmpty()) {
            mFilterEndDate.setTime(-3600000);
        } else if (DateTimeUtils.convertStringToDateForSave(filterEndDate) == null) {
            isError = true;
            mView.setErrorFilterEndDate();
        } else {
            mFilterEndDate = DateTimeUtils.setTimeToDate(DateTimeUtils.convertStringToDateForSave(filterEndDate), 23, 59, 59, false);
        }
        mFilterVenue = filterVenue;
        if (!isError) {
            loadMeetings();
            if (expandOrCollapse)
                mView.expandOrCollapseFilterCardView();
        } else
            loadMeetings();
    }

    @Override
    public void setFilterBeginDate(String filterBeginDate) {
        Calendar calendar = Calendar.getInstance();
        if (filterBeginDate.isEmpty()) {
            mFilterBeginDate.setTime(-3600000);
        } else if (DateTimeUtils.convertStringToDateForSave(filterBeginDate) != null) {
            calendar.clear();
            calendar.setTime(mFilterBeginDate);
            mFilterBeginDate = DateTimeUtils.setTimeToDate(DateTimeUtils.convertStringToDateForSave(filterBeginDate), 0, 0, 0, false);
        }
        mView.launchDatePickerDialog(calendar, ConstantsValues.BEGIN_DATE);
    }

    @Override
    public void setFilterBeginDateManual(String filterBeginDate) {
        Calendar calendar = Calendar.getInstance();
        if (filterBeginDate.isEmpty()) {
            mFilterBeginDate.setTime(-3600000);
        } else if (DateTimeUtils.convertStringToDateForSave(filterBeginDate) != null) {
            calendar.clear();
            calendar.setTime(mFilterBeginDate);
            mFilterBeginDate = DateTimeUtils.setTimeToDate(DateTimeUtils.convertStringToDateForSave(filterBeginDate), 0, 0, 0, false);
        } else
            mView.setErrorFilterBeginDate();
        loadMeetings();
    }

    @Override
    public void setFilterEndDate(String filterEndDate) {
        Calendar calendar = Calendar.getInstance();
        if (filterEndDate.isEmpty()) {
            mFilterEndDate.setTime(-3600000);
        } else if (DateTimeUtils.convertStringToDateForSave(filterEndDate) != null) {
            calendar.clear();
            calendar.setTime(mFilterEndDate);
            mFilterEndDate = DateTimeUtils.setTimeToDate(DateTimeUtils.convertStringToDateForSave(filterEndDate), 23, 59, 59, false);
        }
        mView.launchDatePickerDialog(calendar, ConstantsValues.END_DATE);
    }

    @Override
    public void setFilterEndDateManual(String filterEndDate) {
        Calendar calendar = Calendar.getInstance();
        if (filterEndDate.isEmpty()) {
            mFilterEndDate.setTime(-3600000);
        } else if (DateTimeUtils.convertStringToDateForSave(filterEndDate) != null) {
            calendar.clear();
            calendar.setTime(mFilterEndDate);
            mFilterEndDate = DateTimeUtils.setTimeToDate(DateTimeUtils.convertStringToDateForSave(filterEndDate), 23, 59, 59, false);
        } else
            mView.setErrorFilterEndDate();
        loadMeetings();
    }

    @Override
    public void saveFilterDate(int year, int monthOfYear, int dayOfMonth, boolean beginOrEnd) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        if (beginOrEnd) {
            calendar.set(year, monthOfYear, dayOfMonth);
            mFilterBeginDate.setTime(calendar.getTimeInMillis());
        } else {
            calendar.set(year, monthOfYear, dayOfMonth, 23, 59, 59);
            mFilterEndDate.setTime(calendar.getTimeInMillis());
        }
        loadMeetings();
    }

    @Override
    public void saveFilterVenue(String filterVenue) {
        mFilterVenue = filterVenue;
        loadMeetings();
    }
}
