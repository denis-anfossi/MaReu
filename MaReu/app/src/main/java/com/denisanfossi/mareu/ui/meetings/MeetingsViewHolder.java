package com.denisanfossi.mareu.ui.meetings;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.utils.DateUtils;
import com.google.common.base.Joiner;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.fragment_meetings_cv_item_date_text) TextView mDateText;
    @BindView(R.id.fragment_meetings_cv_item_venue_text) TextView mVenueText;
    @BindView(R.id.fragment_meetings_cv_item_topic_text) TextView mTopicText;
    @BindView(R.id.fragment_meetings_cv_item_delete_btn) ImageButton mDeleteButton;
    @BindView(R.id.fragment_meetings_cv_item_participants_text) TextView mParticipantsText;
    @BindView(R.id.fragment_meetings_cv_item_expand_btn) ImageButton mExpandButton;
    @BindView(R.id.fragment_meetings_cv_item_collapse_btn) ImageButton mCollapseButton;
    @BindString(R.string.empty_meeting_participants_list) String mEmptyMeetingParticipantsList;

    @BindView(R.id.fragment_meetings_card_view) CardView mCardView;

    private Meeting mMeeting;

    public MeetingsViewHolder(@NonNull View itemView, MeetingsAdapter.MeetingsClickListener onClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
        mDeleteButton.setOnClickListener(v -> onClickListener.deleteImageButtonOnClick(v, getLayoutPosition()));
        mExpandButton.setOnClickListener(v -> expandImageButtonClick(v));
        mCollapseButton.setOnClickListener(v -> collapseImageButtonClick(v));
    }


    public void updateWithMeetingData(Meeting meeting) {
        mMeeting = meeting;
        mDateText.setText(DateUtils.convertDateForRecyclerDisplay(meeting.getDate()));
        mVenueText.setText(meeting.getVenue());
        mTopicText.setText(meeting.getTopic());
        setParticipantsList();
    }

    private void setParticipantsList() {
        String participants = "Participants:\n\n".concat(Joiner.on("\n").skipNulls().join(mMeeting.getParticipants()));
        if (!participants.equals("Participants:\n\n")) {
            mParticipantsText.setText(participants);
        } else {
            mParticipantsText.setText(mEmptyMeetingParticipantsList);
        }
    }

    private void expandImageButtonClick(View v) {
        expandOrCollapseMeetingDetails(v);
    }

    private void collapseImageButtonClick(View v) {
        expandOrCollapseMeetingDetails(v);
    }

    private void expandOrCollapseMeetingDetails(View v) {
        if (mParticipantsText.getVisibility() == View.GONE) {
            mParticipantsText.setVisibility(View.VISIBLE);
            mCollapseButton.setVisibility(View.VISIBLE);
            mExpandButton.setVisibility(View.GONE);

        } else {
            mParticipantsText.setVisibility(View.GONE);
            mCollapseButton.setVisibility(View.GONE);
            mExpandButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        expandOrCollapseMeetingDetails(v);
    }
}
