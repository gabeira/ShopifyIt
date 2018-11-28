package com.shopifyit

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.shopifyit.view.MainActivity
import com.shopifyit.view.RepositoryAdapter
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun recycleViewBasicTest() {
        Thread.sleep(1000)
        getInstrumentation().waitForIdleSync()

        onView(withId(R.id.swipeRefreshLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.repoList))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RepositoryAdapter.ViewHolder>(0, click()))

        Intents.intended(
            allOf(
                IntentMatchers.hasAction(Matchers.equalTo(Intent.ACTION_VIEW)),
                IntentMatchers.toPackage("com.android.chrome")
            )
        )
    }
}