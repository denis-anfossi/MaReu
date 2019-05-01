package com.denisanfossi.mareu.ui.meetings;

import com.denisanfossi.mareu.BaseMvp;
import com.denisanfossi.mareu.data.model.Meeting;

import java.util.Calendar;
import java.util.List;

public interface MeetingsContract {

    interface View extends BaseMvp.BaseView<Presenter> {
        void showMeetings(List<Meeting> meetings);

        void showNoMeetings();

        void launchMeetingCreationDialogFragment();

        void expandOrCollapseFilterCardView();

        void updateFiltersTextInputLayout(String filterPatternVenue, String filterBeginDate, String filterEndDate);

        void setErrorFilterBeginDate();

        void setErrorFilterEndDate();

        void launchDatePickerDialog(Calendar calendar, boolean beginOrEnd);
    }

    interface Presenter extends BaseMvp.BasePresenter {
        void loadMeetings();

        void createMeeting();

        void deleteMeeting(int position);

        void loadOrUnloadFilters(String filterVenue, String filterBeginDate, String filterEndDate, boolean expandOrCollapse);

        void setFilterBeginDate(String filterBeginDate);

        void setFilterBeginDateManual(String filterBeginDate);

        void setFilterEndDate(String filterEndDate);

        void setFilterEndDateManual(String filterEndDate);

        void saveFilterDate(int year, int monthOfYear, int dayOfMonth, boolean beginOrEnd);

        void saveFilterVenue(String filterVenue);
    }

}
