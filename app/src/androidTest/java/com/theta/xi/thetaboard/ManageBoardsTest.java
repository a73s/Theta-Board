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
public class ManageBoardsTest {

    @Before
    public void setup() {
        // Setup mock to be logged in so we bypass LoginActivity
        MockRequestProxy mock = new MockRequestProxy();
        mock.setSessionValid(true);
        HttpRequestProxy.setInstance(mock);
    }

    @Test
    public void testAddBoard() {
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Navigate to Manage Boards
            onView(withId(R.id.manageBoardsFragment)).perform(click());

            // Click Add Board FAB
            onView(withId(R.id.add_board_button)).perform(click());

            // Enter Code
            onView(withId(R.id.add_board_code_entry)).perform(typeText("TESTCODE"), closeSoftKeyboard());

            // Submit
            onView(withId(R.id.add_board_submit)).perform(click());

            // Verify the new board is displayed in the list
            onView(withText("Joined Board TESTCODE")).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testCreateBoard() {
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Navigate to Manage Boards
            onView(withId(R.id.manageBoardsFragment)).perform(click());

            // Click Create Board FAB
            onView(withId(R.id.create_board_button)).perform(click());

            // Enter Name and Description
            onView(withId(R.id.create_board_name_entry)).perform(typeText("My New Board"), closeSoftKeyboard());
            onView(withId(R.id.create_board_description_entry)).perform(typeText("Description"), closeSoftKeyboard());

            // Submit
            onView(withId(R.id.create_board_submit)).perform(click());

            // Verify the new board is displayed
            onView(withText("My New Board")).check(matches(isDisplayed()));
        }
    }
}
