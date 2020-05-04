package com.fabernovel.statefullayout.robot

import androidx.fragment.app.testing.FragmentScenario
import com.fabernovel.statefullayout.sample.R.id
import com.fabernovel.statefullayout.sample.ui.empty.EmptyFragment
import com.fabernovel.statefullayout.utils.StatefulLayoutRobot

fun `on empty fragment`(func: EmptyRobot.() -> Unit) = EmptyRobot().apply(func)
class EmptyRobot : TestRobot() {
    init {
        FragmentScenario.launchInContainer(
            EmptyFragment::class.java
        )
    }

    fun containsOnlyDefaultStates() {
        StatefulLayoutRobot.hasStateCount(id.empty_stateful_layout, 2)
        StatefulLayoutRobot.containsState(
            id.empty_stateful_layout,
            id.stateLoading
        )
        StatefulLayoutRobot.containsState(
            id.empty_stateful_layout,
            id.stateError
        )
    }

    fun areAllStateGone() {
        StatefulLayoutRobot.isStateHidden(id.stateError)
        StatefulLayoutRobot.isStateHidden(id.stateLoading)
    }
}
