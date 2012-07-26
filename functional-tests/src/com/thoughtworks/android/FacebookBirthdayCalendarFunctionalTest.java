package com.thoughtworks.android;

import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;

public class FacebookBirthdayCalendarFunctionalTest extends ActivityInstrumentationTestCase2<FacebookBirthdayCalendar> {
    public FacebookBirthdayCalendarFunctionalTest() {
        super(FacebookBirthdayCalendar.class);
    }

    public void testFirstFunctionalTest() {
        Solo solo = new Solo(getInstrumentation(), getActivity());
        solo.assertCurrentActivity("", FacebookBirthdayCalendar.class);
    }
}
