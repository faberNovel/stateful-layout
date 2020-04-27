package com.fabernovel.statefullayout.sample.ui.main

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.fabernovel.statefullayout.sample.R
import com.fabernovel.statefullayout.utils.isStatefulLayoutState
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainFragmentTest {
    private lateinit var scenario: FragmentScenario<MainFragment>

    @Before
    fun setUp() {
        scenario = FragmentScenario.launchInContainer(MainFragment::class.java)
    }

    @Test
    fun `when pressing show error, error state should be displayed`() {
        onView(withId(R.id.errorButton)).perform(click())

        onView(isStatefulLayoutState(R.id.stateContent))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateLoading))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateCustom))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateError))
            .check(matches(withEffectiveVisibility(VISIBLE)))
    }

    @Test
    fun `when pressing show loading, loading state should be displayed`() {
        onView(withId(R.id.loadingButton)).perform(click())

        onView(isStatefulLayoutState(R.id.stateContent))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateError))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateCustom))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateLoading))
            .check(matches(withEffectiveVisibility(VISIBLE)))
    }

    @Test
    fun `when pressing show content, content state should be displayed`() {
        onView(withId(R.id.contentButton)).perform(click())

        onView(isStatefulLayoutState(R.id.stateLoading))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateError))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateCustom))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateContent))
            .check(matches(withEffectiveVisibility(VISIBLE)))
    }

    @Test
    fun `when pressing show custom, custom state should be displayed`() {
        onView(withId(R.id.customButton)).perform(click())

        onView(isStatefulLayoutState(R.id.stateLoading))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateError))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateContent))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(isStatefulLayoutState(R.id.stateCustom))
            .check(matches(withEffectiveVisibility(VISIBLE)))
    }
}
