package com.fabernovel.statefullayout.sample.ui.empty

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.fabernovel.statefullayout.sample.R
import com.fabernovel.statefullayout.utils.withStatefulLayoutStateId
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class EmptyFragmentTest {

    private lateinit var scenario: FragmentScenario<EmptyFragment>

    @Before
    fun setUp() {
        scenario = FragmentScenario.launchInContainer(EmptyFragment::class.java)
    }

    @Test
    fun `given a stateful with no state, it should contains only two states`() {
        onView(withId(R.id.empty_stateful_layout))
            .check(matches(hasChildCount(2)))
        onView(withId(R.id.empty_stateful_layout))
            .check(matches(hasDescendant(withId(R.id.stateError))))
        onView(withId(R.id.empty_stateful_layout))
            .check(matches(hasDescendant(withId(R.id.stateLoading))))
    }

    @Test
    fun `given a stateful layout has no initial state, no state should be visible`() {
        onView(withStatefulLayoutStateId(R.id.stateLoading))
            .check(matches(withEffectiveVisibility(GONE)))
        onView(withStatefulLayoutStateId(R.id.stateError))
            .check(matches(withEffectiveVisibility(GONE)))
    }
}
