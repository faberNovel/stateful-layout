package com.fabernovel.statefullayout.robot

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.FragmentScenario.launchInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.fabernovel.statefullayout.sample.R
import com.fabernovel.statefullayout.sample.ui.empty.EmptyFragment
import com.fabernovel.statefullayout.sample.ui.main.MainFragment
import com.fabernovel.statefullayout.utils.withStatefulLayoutStateId

open class TestRobot {
    fun clickOn(resId: Int): ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.click())
    }

    fun hasVisibility(resId: Int, visibility: Visibility): ViewInteraction {
        return onView(withId(resId))
            .check(matches(withEffectiveVisibility(visibility)))
    }
}

open class StatefulLayoutRobot : TestRobot() {

    fun isStateHidden(stateId: Int) = hasVisibility(stateId, GONE)
        .check(matches(withStatefulLayoutStateId(stateId)))

    fun isStateVisible(stateId: Int) = hasVisibility(stateId, VISIBLE)
        .check(matches(withStatefulLayoutStateId(stateId)))

    fun containsState(statefulId: Int, stateId: Int): ViewInteraction = onView(withId(statefulId))
        .check(matches(hasDescendant(withStatefulLayoutStateId(stateId))))

    fun hasStateCount(statefulId: Int, count: Int): ViewInteraction = onView(withId(statefulId))
        .check(matches(hasChildCount(count)))
}

fun `on main fragment`(func: MainRobot.() -> Unit) = MainRobot().apply(func)

class MainRobot : StatefulLayoutRobot() {
    init {
        launchInContainer(MainFragment::class.java)
    }

    fun clickOnContentButton() = clickOn(R.id.contentButton)

    fun clickOnLoadingButton() = clickOn(R.id.loadingButton)

    fun clickOnErrorButton() = clickOn(R.id.errorButton)

    fun clickOnCustomButton() = clickOn(R.id.customButton)

    fun isContentStateDisplayed() {
        isStateVisible(R.id.stateContent)
        isStateHidden(R.id.stateCustom)
        isStateHidden(R.id.stateError)
        isStateHidden(R.id.stateLoading)
    }

    fun isCustomStateDisplayed() {
        isStateVisible(R.id.stateCustom)
        isStateHidden(R.id.stateContent)
        isStateHidden(R.id.stateError)
        isStateHidden(R.id.stateLoading)
    }

    fun isErrorStateDisplayed() {
        isStateVisible(R.id.stateError)
        isStateHidden(R.id.stateContent)
        isStateHidden(R.id.stateCustom)
        isStateHidden(R.id.stateLoading)
    }

    fun isLoadingStateDisplayed() {
        isStateVisible(R.id.stateLoading)
        isStateHidden(R.id.stateContent)
        isStateHidden(R.id.stateCustom)
        isStateHidden(R.id.stateError)
    }
}

fun `on empty fragment`(func: EmptyRobot.() -> Unit) = EmptyRobot().apply(func)

class EmptyRobot : StatefulLayoutRobot() {
    init {
        FragmentScenario.launchInContainer(EmptyFragment::class.java)
    }

    fun containsOnlyDefaultStates() {
        hasStateCount(R.id.empty_stateful_layout, 2)
        containsState(R.id.empty_stateful_layout, R.id.stateLoading)
        containsState(R.id.empty_stateful_layout, R.id.stateError)
    }

    fun areAllStateGone() {
        isStateHidden(R.id.stateError)
        isStateHidden(R.id.stateLoading)
    }
}
