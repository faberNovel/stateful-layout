package com.fabernovel.statefullayout.test.ui.override

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.fabernovel.statefullayout.test.R
import com.fabernovel.statefullayout.utils.containsState
import com.fabernovel.statefullayout.utils.hasStateCount
import com.fabernovel.statefullayout.utils.withStatefulLayoutStateId

fun `on override default fragment`(func: OverrideDefaultRobot.() -> Unit) =
    OverrideDefaultRobot().apply(func)

class OverrideDefaultRobot {
    init {
        FragmentScenario.launchInContainer(OverrideDefaultFragment::class.java)
    }

    fun containsOnlyOverrideDefaultStates() {
        hasStateCount(R.id.stateful_layout, 2)
        containsState(
            R.id.stateful_layout,
            R.id.stateLoading
        )
        containsState(
            R.id.stateful_layout,
            R.id.stateError
        )
    }

    fun hasOverriddenLoadingState() {
        withStatefulLayoutStateId(R.id.stateLoading)
            .matches(hasDescendant(withId(R.id.custom_error_text)))
    }

    fun hasOverriddenErrorState() {
        withStatefulLayoutStateId(R.id.stateError)
            .matches(hasDescendant(withId(R.id.custom_loading_text)))
    }
}
