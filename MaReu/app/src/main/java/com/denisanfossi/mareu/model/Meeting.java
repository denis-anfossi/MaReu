package com.denisanfossi.mareu.model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Meeting {
    private String mTopic;
    private Date mDate;
    private String mVenue;
    private Set<String> mParticipants;


    public Meeting(String topic, Date date, String venue) {
        this.mTopic = topic;
        this.mDate = date;
        this.mVenue = venue;
        this.mParticipants = new TreeSet<>();
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getVenue() {
        return mVenue;
    }

    public void setVenue(String venue) {
        mVenue = venue;
    }

    public Set<String> getParticipants() {
        return mParticipants;
    }

    public void addParticipant(String participant) {
        mParticipants.add(participant);
    }
}
