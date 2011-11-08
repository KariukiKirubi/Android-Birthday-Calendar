package com.thoughtworks.android;

import android.content.Intent;
import com.facebook.android.Facebook;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class FacebookBirthdayCalendarTest {
    @Mock
    Facebook facebook;
    @Mock
    Calendar calendar;
    FacebookBirthdayCalendar facebookBirthdayCalendar;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        facebookBirthdayCalendar = new FacebookBirthdayCalendar();
        setField("facebook", facebook);
        setField("calendar", calendar);
    }

    @Test
    public void shouldInvokeFacebookAuthorizeCallback() throws NoSuchFieldException, IllegalAccessException {
        int resultCode = 456;
        int requestCode = 123;
        Intent data = new Intent();
        facebookBirthdayCalendar.onActivityResult(requestCode, resultCode, data);

        verify(facebook).authorizeCallback(requestCode, resultCode, data);
    }

    private void setField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = facebookBirthdayCalendar.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(facebookBirthdayCalendar, value);
    }
}
