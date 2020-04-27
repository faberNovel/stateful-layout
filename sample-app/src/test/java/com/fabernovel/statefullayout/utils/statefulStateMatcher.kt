package com.fabernovel.statefullayout.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.fabernovel.statefullayout.StatefulLayout
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo

fun isStatefulLayoutState(@IdRes stateId: Int): Matcher<View> =
    allOf(withId(stateId), withParent(withClassName(equalTo(StatefulLayout::class.java.name))))
