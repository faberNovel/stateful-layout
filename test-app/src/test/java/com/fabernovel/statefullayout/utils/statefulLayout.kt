package com.fabernovel.statefullayout.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.fabernovel.statefullayout.StatefulLayout
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo

fun withStatefulLayoutStateId(@IdRes stateId: Int): Matcher<View> =
    allOf(withId(stateId), withParent(withClassName(equalTo(StatefulLayout::class.java.name))))

fun isStateHidden(stateId: Int): ViewInteraction = hasVisibility(stateId, GONE)
    .check(matches(withStatefulLayoutStateId(stateId)))

fun isStateVisible(stateId: Int): ViewInteraction = hasVisibility(stateId, VISIBLE)
    .check(matches(withStatefulLayoutStateId(stateId)))

fun containsState(statefulId: Int, stateId: Int): ViewInteraction = onView(withId(statefulId))
    .check(matches(hasDescendant(withStatefulLayoutStateId(stateId))))

fun hasStateCount(statefulId: Int, count: Int): ViewInteraction = onView(withId(statefulId))
    .check(matches(hasChildCount(count)))
