package com.example.wehelie.dapp;


import android.content.pm.ActivityInfo;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {




    private String Name = "Layth";
    private String Email = "layth@gmail.com";
    private String Username = "aardvark";
    private String Dob = "02/05/1985";
    private String age = "32";
    private String Job = "Geographer";

    private String Bio = " I am Layth. I am 32 years old";

    private MainActivity mActivity;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);




    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void testToolBar() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));
    }

    @Test
    public void testSwipePage() {
        onView(withId(R.id.viewpager))
                .check(matches(isDisplayed()));
    }




    @Test
    public void testTabSwitching() {
        onView(withId(R.id.tabs))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testTabsDisplay() {
        onView(withId(R.id.tabs))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSubmitButton() {

        onView(withId(R.id.goback)).perform(click());

    }

    @Test
    public void testFormLayout() {
        onView(withId(R.id.goback)).perform(click());
        onView((withId(R.id.relativeLayout))).check(matches(isDisplayed()));
    }



    @Test
    public void testName() {
        onView(withId(R.id.goback)).perform(click());
        onView(withId(R.id.editName)).perform(typeText(Name));
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
    }

    @Test
    public void testUserName() {
        onView(withId(R.id.goback)).perform(click());
        onView(withId(R.id.editUsername)).perform(typeText(Username));
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
    }

    @Test
    public void testUserDOB() {
        onView(withId(R.id.goback)).perform(click());
        onView(withId(R.id.editDob)).perform(typeText(Dob));
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
    }

    @Test
    public void rotateScreenTest(){

        onView(withId(R.id.goback)).perform(click());
       onView(withId(R.id.editName)).perform(typeText(Name));
        onView(withId(R.id.editName)).check(matches(withText(Name)));

        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Test
    public void testSettingsTabEmail() {
        String userEmail = "layth@google.com";
        clickOnTabSettings();
        onView(withId(R.id.userEmail)).perform(typeText(userEmail));

    }

    @Test
    public void testSettingsTabGender() {
        String gender = "Male";
        clickOnTabSettings();
        onView(withId(R.id.gender)).perform(typeText(gender));

    }

    @Test
    public void testSettingsTabMaxDis() {
        String max = "20 miles";
        clickOnTabSettings();
        onView(withId(R.id.max)).perform(typeText(max));

    }


    @Test
    public void testSettingsTabMin() {
        String min = "5 Miles";
        clickOnTabSettings();
        onView(withId(R.id.min)).perform(typeText(min));

    }

    @Test
    public void testSettingsTabPrivacy() {
        String privacy = "Public";
        clickOnTabSettings();
        onView(withId(R.id.gender)).perform(typeText(privacy));

    }


    @Test
    public void testSettingsTabReminder() {
        String reminder = "Everyday at 4pm";
        clickOnTabSettings();
        onView(withId(R.id.gender)).perform(typeText(reminder));

    }

    @Test
    public void testSettingsTabMinAge() {
        String minAge = "20";
        clickOnTabSettings();
        onView(withId(R.id.minage)).perform(typeText(minAge));

    }

    @Test
    public void testSettingsTabMaxAge() {
        String maxage = "35";
        clickOnTabSettings();
        onView(withId(R.id.gender)).perform(typeText(maxage));

    }


    @Test
    public void testSettingsTabSaveButton() {
        clickOnTabSettings();
        onView(withId(R.id.settingsButton)).perform(click());

    }

    @Test
    public void testSettingsTabSaveButtonEmptyFields() {
        clickOnTabSettings();

        testSettingsTabEmail();
        testSettingsTabGender();
        testSettingsTabMaxDis();
        testSettingsTabMin();
        testSettingsTabMinAge();
        testSettingsTabMaxAge();
        testSettingsTabPrivacy();
        testSettingsTabReminder();
        onView(withId(R.id.settingsButton)).perform(click());
    }




    @Test
    public void testUserEmail() {
        onView(withId(R.id.goback)).perform(click());
        onView(withId(R.id.editEmail)).perform(typeText(Email));
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
    }

    @Test
    public void testEmailFieldDisplayed() {
        onView(withId(R.id.goback)).perform(click());
        onView((withId(R.id.editEmail))).check(matches(isDisplayed()));
    }


    @Test
    public void testUserJob() {
        onView(withId(R.id.goback)).perform(click());
        onView(withId(R.id.editJob)).perform(typeText(Job));
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
    }

    @Test
    public void testUserBio() {
        onView(withId(R.id.goback)).perform(click());
        onView(withId(R.id.editBio)).perform(typeText(Bio));
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
    }

    @Test
    public void testConstants() {
        assertEquals(Constants.KEY_NAME, "name");
        assertEquals(Constants.KEY_AGE, "age");
        assertEquals(Constants.KEY_EMAIL, "email");
        assertEquals(Constants.KEY_USERNAME, "username");
        assertEquals(Constants.KEY_DESCRIPTION, "description");
    }


    @Test
    public void clickOnTabMatches() {
        Matcher<View> matcher = allOf(withText("Matches"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
    }

    @Test
    public void clickOnTabSettings() {
        Matcher<View> matcher = allOf(withText("Settings"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
    }

    @Test
    public void scrollToSpecificRecycleViewer() {
        Matcher<View> matcher = allOf(withText("Matches"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        onView(withId(R.id.my_recycler_view)).perform(scrollToPosition(3));
    }

    @Test
    public void clickOnRecyclerItem() {
        Matcher<View> matcher = allOf(withText("Matches"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        onView(withId(R.id.my_recycler_view)).perform(scrollToPosition(3), click());
    }




    @Test
    public void checkFireBaseChildExists() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("matches")) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Test
    public void checkRecyclerViewActions() {
        clickOnTabMatches();
        onView(withId(R.id.my_recycler_view))
                .check(matches(hasDescendant(withText("Cool Guy Mike"))));
    }

//    @Test
//    public void testRecyclerView() {
//        onView(withId(R.id.my_recycler_view))
//                .perform(scrollToPosition(R.string.like_button))
//                .check(matches(hasDescendant(withText(R.string.like_button))));
//    }





}


