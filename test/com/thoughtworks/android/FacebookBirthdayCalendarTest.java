package com.thoughtworks.android;

import android.os.Bundle;
import com.facebook.android.Facebook;
import com.thoughtworks.android.listener.AuthorizationDialogListener;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
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
    public void facebookShouldAuthorizeUser() throws NoSuchFieldException, IllegalAccessException {
        facebookBirthdayCalendar.onCreate(new Bundle());

        ArgumentCaptor<String[]> projectionsCaptor = ArgumentCaptor.forClass(String[].class);
        ArgumentCaptor<AuthorizationDialogListener> dialogListenerCaptor = ArgumentCaptor.forClass(AuthorizationDialogListener.class);
        verify(facebook).authorize(eq(facebookBirthdayCalendar), projectionsCaptor.capture(), dialogListenerCaptor.capture());
        assertEquals(1, projectionsCaptor.getValue().length);
        assertEquals("friends_birthday", projectionsCaptor.getValue()[0]);
        assertFieldValueOfObject(dialogListenerCaptor.getValue(), "facebook", facebook);
        assertFieldValueOfObject(dialogListenerCaptor.getValue(), "facebookListener", facebookBirthdayCalendar);
    }

    private <T> void assertFieldValueOfObject(Object object, String fieldName, T expectedValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        T actualValue = (T) field.get(object);
        assertEquals(expectedValue, actualValue);
    }

    private void setField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = facebookBirthdayCalendar.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(facebookBirthdayCalendar, value);
    }
}
