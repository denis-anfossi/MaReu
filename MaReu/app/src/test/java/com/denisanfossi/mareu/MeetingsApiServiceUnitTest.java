package com.denisanfossi.mareu;

import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;
import com.denisanfossi.mareu.service.MeetingsApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class MeetingsApiServiceUnitTest {

    private MeetingsApiService mMeetingsApiService;
    private Meeting mMeetingOne;
    private Meeting mMeetingTwo;
    private Meeting mMeetingThree;

    @Before
    public void setup() {
        mMeetingsApiService = DI.getNewInstanceMeetingsApiService();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2019, 04, 30, 21, 42);
        mMeetingOne = new Meeting("Topic", new Date(calendar.getTimeInMillis()), "Venue");
        mMeetingOne.addParticipant("toto@toto.fr");
        mMeetingOne.addParticipant("coucou@coucou.com");
        mMeetingOne.addParticipant("hello@hello.com");
        mMeetingOne.addParticipant("adressemail@adressemail.com");
        calendar.set(2019, 04, 30, 23, 42);
        mMeetingTwo = new Meeting("Topic two", new Date(calendar.getTimeInMillis()), "Venue two");
        mMeetingTwo.addParticipant("email@email.fr");
        calendar.set(2019, 05, 01, 8, 42);
        mMeetingThree = new Meeting("Topic three", new Date(calendar.getTimeInMillis()), "Venue three");
    }

    @Test
    public void getStartMeetingsWithSuccess() {
        List<Meeting> meetings = mMeetingsApiService.getMeetings();
        List<Meeting> expectedMeetings = new ArrayList<>();
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void addMeetingWithSuccess() {
        mMeetingsApiService.addMeeting(mMeetingOne);
        assertEquals(mMeetingOne.getTopic(), mMeetingsApiService.getMeetings().get(0).getTopic());
        assertEquals(mMeetingOne.getVenue(), mMeetingsApiService.getMeetings().get(0).getVenue());
        assertEquals(mMeetingOne.getDate(), mMeetingsApiService.getMeetings().get(0).getDate());
        assertTrue(mMeetingsApiService.getMeetings().get(0).getParticipants().containsAll(mMeetingOne.getParticipants()));
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingOne));
        mMeetingsApiService.addMeeting(mMeetingTwo);
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingTwo));
        mMeetingsApiService.addMeeting(mMeetingThree);
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingThree));
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> expectedMeetings = new ArrayList<>();
        expectedMeetings.add(mMeetingOne);
        expectedMeetings.add(mMeetingTwo);
        expectedMeetings.add(mMeetingThree);

        mMeetingsApiService.addMeeting(mMeetingOne);
        mMeetingsApiService.addMeeting(mMeetingTwo);
        mMeetingsApiService.addMeeting(mMeetingThree);
        assertThat(mMeetingsApiService.getMeetings(), IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        mMeetingsApiService.addMeeting(mMeetingOne);
        mMeetingsApiService.addMeeting(mMeetingTwo);
        mMeetingsApiService.addMeeting(mMeetingThree);
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingOne));
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingTwo));
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingThree));

        mMeetingsApiService.deleteMeeting(mMeetingOne);
        assertFalse(mMeetingsApiService.getMeetings().contains(mMeetingOne));
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingTwo));
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingThree));
        mMeetingsApiService.deleteMeeting(mMeetingTwo);
        assertFalse(mMeetingsApiService.getMeetings().contains(mMeetingTwo));
        assertTrue(mMeetingsApiService.getMeetings().contains(mMeetingThree));
        mMeetingsApiService.deleteMeeting(mMeetingThree);
        assertFalse(mMeetingsApiService.getMeetings().contains(mMeetingThree));
    }

    @Test
    public void sortMeetingsByDateWithSuccess() {
        mMeetingsApiService.addMeeting(mMeetingTwo);
        mMeetingsApiService.addMeeting(mMeetingOne);
        mMeetingsApiService.addMeeting(mMeetingThree);
        assertEquals(mMeetingOne, mMeetingsApiService.getMeetings().get(1));
        assertEquals(mMeetingTwo, mMeetingsApiService.getMeetings().get(0));
        assertEquals(mMeetingThree, mMeetingsApiService.getMeetings().get(2));

        Collections.sort(mMeetingsApiService.getMeetings());
        assertEquals(mMeetingOne, mMeetingsApiService.getMeetings().get(0));
        assertEquals(mMeetingTwo, mMeetingsApiService.getMeetings().get(1));
        assertEquals(mMeetingThree, mMeetingsApiService.getMeetings().get(2));
    }
}