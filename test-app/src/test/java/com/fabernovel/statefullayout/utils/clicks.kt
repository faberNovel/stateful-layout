package com.fabernovel.statefullayout.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers

fun clickOn(resId: Int): ViewInteraction = onView(ViewMatchers.withId(resId))
    .perform(ViewActions.click())
