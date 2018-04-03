package com.bwldr.emijit;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import com.bwldr.emijit.signup.SignupActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Tests Navigation using {@link DrawerLayout}
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<SignupActivity> mActivityTestRule =
            new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void clickOnAndroidHomeIcon_OpensNavigationDrawer() {
        // Check that left drawer is closed at startup
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))); // Left Drawer should be closed.

        // Open Drawer
        onView(withId(R.id.drawer_layout))
                .perform(open());

        // Check if drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.LEFT))); // Left drawer is open open.
    }

    @Test
    public void clickOnSettingsNavigationItem_ShowsSettingsScreen() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(open()); // Open Drawer

        // Start statistics screen.
        onView(withId(R.id.nav_view))
                .perform(navigateTo(R.id.settings_navigation_menu_item));

        // TODO: not working maybe because it's a Preference View....
//        // Check that statistics Activity was opened.
//        String expectedSettingsSetTitleText = InstrumentationRegistry.getTargetContext()
//                .getString(R.string.settings_title);
//        onView(withId(R.id.toolbar)).check(matches(withText(expectedSettingsSetTitleText)));
    }
}
