package com.denisanfossi.mareu.ui.meetings;

import com.denisanfossi.mareu.BaseMvp;
import com.denisanfossi.mareu.data.model.Meeting;

import java.util.List;

public interface MeetingsContract {

    interface View extends BaseMvp.BaseView<Presenter> {
        void showMeetings(List<Meeting> meetings);

        void showNoMeetings();

        void showMeetingDetails(Meeting meeting);

        void launchMeetingCreationDialogFragment();
    }

    interface Presenter extends BaseMvp.BasePresenter {
        void loadMeetings();

        void createMeeting();

        void deleteMeeting(int position);
    }

}
