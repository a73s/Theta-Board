package com.theta.xi.thetaboard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup() {
        // Inject the mock network proxy before every test
        HttpRequestProxy.setInstance(new MockRequestProxy());
    }

    @Test
    public void testLoginFlow() {
        // Type email
        onView(withId(R.id.login_email_text))
                .perform(typeText("test@example.com"), closeSoftKeyboard());

        // Type password
        onView(withId(R.id.login_password_text))
                .perform(typeText("password123"), closeSoftKeyboard());

        // Click login
        onView(withId(R.id.login_button)).perform(click());

        // Verify we navigated to MainActivity by checking for the bottom navigation view
        // Note: transitions might take time, but Espresso waits for idle resources.
        // If MainActivity starts, this view should be visible.
        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginScreenDisplay() {
        // Verify initial state
        onView(withId(R.id.login_screen_title)).check(matches(withText("Login")));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }
}
