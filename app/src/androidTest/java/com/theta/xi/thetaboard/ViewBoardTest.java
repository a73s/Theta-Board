package com.theta.xi.thetaboard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ViewBoardTest {

    @Before
    public void setup() {
        MockRequestProxy mock = new MockRequestProxy();
        mock.setSessionValid(true);
        HttpRequestProxy.setInstance(mock);
    }

    @Test
    public void testViewBoardAndCreatePost() {
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // 1. Verify we see the board list (General Announcements)
            onView(withText("General Announcements")).check(matches(isDisplayed()));

            // 2. Click on the board
            onView(withText("General Announcements")).perform(click());

            // 3. Verify we are in ViewBoardActivity and see existing posts
            onView(withText("Welcome!")).check(matches(isDisplayed()));
            onView(withText("Update")).check(matches(isDisplayed()));

            // 4. Click Create Post Button
            onView(withId(R.id.create_post_button)).perform(click());

            // 5. Verify Create Post Activity
            onView(withId(R.id.editTextPostTitle)).perform(typeText("My New Post"), closeSoftKeyboard());
            onView(withId(R.id.editTextPostBody)).perform(typeText("This is a test post."), closeSoftKeyboard());

            // 6. Submit
            onView(withId(R.id.buttonSubmitPost)).perform(click());

            // 7. Verify we are back (check for the new post)
            onView(withText("My New Post")).check(matches(isDisplayed()));
        }
    }
}
