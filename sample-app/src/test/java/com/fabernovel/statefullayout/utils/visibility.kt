package com.fabernovel.statefullayout.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.Visibility

fun hasVisibility(resId: Int, visibility: Visibility): ViewInteraction {
    return onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility)))
}
