package com.denisanfossi.mareu.ui.meetings;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.denisanfossi.mareu.R;
import com.denisanfossi.mareu.data.model.Meeting;
import com.denisanfossi.mareu.di.DI;
import com.denisanfossi.mareu.service.MeetingsApiService;
import com.denisanfossi.mareu.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.denisanfossi.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingsListTest {

    @Rule
    public ActivityTestRule<MeetingsActivity> mActivityTestRule = new ActivityTestRule<>(MeetingsActivity.class);
    private MeetingsActivity mActivity;
    private MeetingsApiService mMeetingsApiService;
    private Meeting mMeetingOne;
    private Meeting mMeetingTwo;
    private Meeting mMeetingThree;

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());

        mMeetingsApiService = DI.getNewInstanceMeetingsApiService();
        assertThat(mMeetingsApiService, notNullValue());

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
        mMeetingsApiService.addMeeting(mMeetingOne);
        mMeetingsApiService.addMeeting(mMeetingTwo);
        mMeetingsApiService.addMeeting(mMeetingThree);
        onView(allOf(withId(R.id.fragment_meetings_card_view_filter), isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.fragment_meetings_card_view_filter), isDisplayed()))
                .perform(click());
    }

    @Test
    public void myMeetingsListTest_shouldNotBeEmpty() {
        onView(allOf(withId(R.id.fragment_meetings_recycler_view), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myMeetingsListTest_shouldContainThreeMeetings() {
        onView(allOf(withId(R.id.fragment_meetings_recycler_view), isDisplayed()))
                .check(withItemCount(3));
    }

    @Test
    public void myMeetingsListTest_createAction_shouldAddItem() {
        onView(allOf(withId(R.id.activity_meetings_add_meeting_fab), isDisplayed()))
                .perform(click());
        onView(allOf(childAtPosition(childAtPosition(withId(R.id.fragment_meeting_creation_topic_text_input), 0), 0), isDisplayed()))
                .perform(replaceText("Topic"), closeSoftKeyboard());
        onView(allOf(childAtPosition(childAtPosition(withId(R.id.fragment_meeting_creation_venue_text_input), 0), 0), isDisplayed()))
                .perform(replaceText("Venue"), closeSoftKeyboard());
        onView(allOf(withId(R.id.meeting_creation_dialog_toolbar_save_item), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.fragment_meetings_recycler_view), isDisplayed()))
                .check(withItemCount(4));
    }

    @Test
    public void meetingsListTest_deleteAction_shouldRemoveItem() {
        onView(allOf(withId(R.id.fragment_meetings_recycler_view), isDisplayed()))
                .perform(actionOnItemAtPosition(2, new DeleteViewAction()))
                .check(withItemCount(2));
    }

    @Test
    public void meetingsListTest_filterAction_shouldHideItem() {
        onView(allOf(withId(R.id.fragment_meetings_card_view_filter), isDisplayed()))
                .perform(click());
        onView(allOf(childAtPosition(childAtPosition(withId(R.id.fragment_meetings_card_view_filter_venue_layout), 0), 0), isDisplayed()))
                .perform(replaceText("Venue t"), closeSoftKeyboard());
        onView(allOf(withId(R.id.fragment_meetings_card_view_filter_apply_btn), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.fragment_meetings_recycler_view), isDisplayed()))
                .check(withItemCount(2));
    }
}
