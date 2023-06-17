package com.example.githubapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test

class MainActivityTest{

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkActivityVisibility(){
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun checkMenuItemVisibility(){
        onView(withId(R.id.btnSearch)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSetting)).check(matches(isDisplayed()))
    }

    @Test
    fun checkListAccountVisibility(){
        onView(withId(R.id.rvAccount)).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToFavorite(){
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withId(R.id.layout_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToSetting(){
        onView(withId(R.id.btnSetting)).perform(click())
        onView(withId(R.id.layout_setting)).check(matches(isDisplayed()))
    }
}