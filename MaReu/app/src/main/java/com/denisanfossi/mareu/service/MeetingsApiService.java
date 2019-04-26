package com.denisanfossi.mareu.service;

import com.denisanfossi.mareu.data.model.Meeting;

import java.util.List;

/**
 * MeetingsContract API Service Interface
 */
public interface MeetingsApiService {

    /**
     * get all meetings
     *
     * @return @{@link List<Meeting>}   the list of all meetings
     */
    List<Meeting> getMeetings();

    /**
     * add a meeting
     *
     * @param meeting the meeting to add
     */
    void addMeeting(Meeting meeting);

    /**
     * delete a meeting
     *
     * @param meeting the meeting to delete
     */
    void deleteMeeting(Meeting meeting);
}
