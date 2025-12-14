package com.theta.xi.thetaboard;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.theta.xi.thetaboard", appContext.getPackageName());
    }

    @Test
    public void useMockProxy() {
        // Set the mock proxy
        HttpRequestProxy.setInstance(new MockRequestProxy());

        // Get the proxy (should be the mock)
        IRequestProxy proxy = HttpRequestProxy.getProxy();

        // Verify it behaves like the mock
        assertTrue(proxy.login("test@test.com", "password"));
        assertEquals(3, proxy.getAllBoardsForUser().size());
    }
}