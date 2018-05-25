package com.example.wehelie.dapp;


import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
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
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.anyOf;
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

//        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));
    }

    @Test
    public void clickOnRecyclerItem() {
        Matcher<View> matcher = allOf(withText("Matches"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.scrollToPosition(3), click());
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





}


