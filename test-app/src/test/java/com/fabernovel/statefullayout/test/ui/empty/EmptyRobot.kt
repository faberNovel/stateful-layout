package com.fabernovel.statefullayout.test.ui.empty

import androidx.fragment.app.testing.FragmentScenario
import com.fabernovel.statefullayout.test.R.id
import com.fabernovel.statefullayout.utils.containsState
import com.fabernovel.statefullayout.utils.hasStateCount
import com.fabernovel.statefullayout.utils.isStateHidden

fun `on empty fragment`(func: EmptyRobot.() -> Unit) = EmptyRobot()
    .apply(func)

class EmptyRobot {
    init {
        FragmentScenario.launchInContainer(
            EmptyFragment::class.java
        )
    }

    fun containsOnlyDefaultStates() {
        hasStateCount(id.empty_stateful_layout, 2)
        containsState(
            id.empty_stateful_layout,
            id.stateLoading
        )
        containsState(
            id.empty_stateful_layout,
            id.stateError
        )
    }

    fun areAllStateGone() {
        isStateHidden(id.stateError)
        isStateHidden(id.stateLoading)
    }
}
