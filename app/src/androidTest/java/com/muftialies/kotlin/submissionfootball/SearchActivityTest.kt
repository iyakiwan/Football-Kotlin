package com.muftialies.kotlin.submissionfootball

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(SearchActivity::class.java)

    @Test
    fun testItemInSearchActivity() {
        //Cek Displayed editText and bottom
        onView(withId(R.id.etSearchEvent))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.btSearchEvent))
            .check(ViewAssertions.matches(isDisplayed()))
        //cek value edit text
        onView(withId(R.id.etSearchEvent))
            .perform(typeText("Man United"))
        onView(withId(R.id.etSearchEvent))
            .check(ViewAssertions.matches(withText("Man United")))
        //Cek Displayed recyclerView and progressBar
        onView(withId(R.id.btSearchEvent))
            .perform(click())
        onView(withId(R.id.pbSearchEvent))
            .check(ViewAssertions.matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.rvSearchEvent))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testActionSearchInSearchActivity() {
        //1. search before input
        onView(withId(R.id.btSearchEvent))
            .perform(click())
        /*onView(withText(R.string.alertEmptySearch))
            .check(ViewAssertions.matches(isDisplayed()))*/

        //2. search with input random
        onView(withId(R.id.etSearchEvent))
            .perform(typeText("This is The Test"))
        onView(withId(R.id.btSearchEvent))
            .perform(click())
        Thread.sleep(5000)
        /*onView(withText(R.string.alertSearch))
            .check(ViewAssertions.matches(isDisplayed()))*/

        //3. search with true input
        onView(withId(R.id.etSearchEvent))
            .perform(replaceText("United"))
        onView(withId(R.id.btSearchEvent))
            .perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.rvSearchEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        onView(withId(R.id.rvSearchEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        /*onView(withId(R.id.rvSearchEvent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()))*/
    }

    @Test
    fun testValueSearchView(){
        //1. search first value
        onView(withId(R.id.etSearchEvent))
            .perform(typeText("Barcelona"))
        onView(withId(R.id.btSearchEvent))
            .perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.rvSearchEvent))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rvSearchEvent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()))
        Thread.sleep(10000)
        onView(withId(R.id.activity_match_detail))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())

        //2. search second value
        onView(withId(R.id.etSearchEvent))
            .perform(replaceText("Madrid"))
        onView(withId(R.id.btSearchEvent))
            .perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.rvSearchEvent))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rvSearchEvent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()))
        Thread.sleep(10000)
        onView(withId(R.id.activity_match_detail))
            .check(ViewAssertions.matches(isDisplayed()))


    }
}