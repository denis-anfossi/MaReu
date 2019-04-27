package com.denisanfossi.mareu.ui.meetings;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.fragment_meetings_cv_item_date_text) TextView mTextDate;
    @BindView(R.id.fragment_meetings_cv_item_venue_text) TextView mTextVenue;
    @BindView(R.id.fragment_meetings_cv_item_topic_text) TextView mTextTopic;
    @BindView(R.id.fragment_meetings_cv_item_delete_btn) ImageButton mDeleteButton;
    private Meeting mMeeting;

    public MeetingsViewHolder(@NonNull View itemView, MeetingsAdapter.MeetingsClickListener onClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
        mDeleteButton.setOnClickListener(v -> {
            onClickListener.deleteImageButtonOnClick(v, getLayoutPosition());
        });
    }

    public void updateWithMeetingData(Meeting meeting) {
        mTextDate.setText(DateUtils.convertDateForRecyclerDisplay(meeting.getDate()));
        mTextVenue.setText(meeting.getVenue());
        mTextTopic.setText(meeting.getTopic());
    }

    @Override
    public void onClick(View v) {
        // TODO
    }
}
