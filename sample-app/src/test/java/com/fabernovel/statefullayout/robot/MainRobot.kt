package com.fabernovel.statefullayout.robot

import androidx.fragment.app.testing.FragmentScenario
import com.fabernovel.statefullayout.sample.R.id
import com.fabernovel.statefullayout.sample.ui.main.MainFragment
import com.fabernovel.statefullayout.utils.StatefulLayoutRobot

fun `on main fragment`(func: MainRobot.() -> Unit) = MainRobot().apply(func)

class MainRobot {
    init {
        FragmentScenario.launchInContainer(
            MainFragment::class.java
        )
    }

    fun clickOnContentButton() = clickOn(id.contentButton)

    fun clickOnLoadingButton() = clickOn(id.loadingButton)

    fun clickOnErrorButton() = clickOn(id.errorButton)

    fun clickOnCustomButton() = clickOn(id.customButton)

    fun isContentStateDisplayed() {
        StatefulLayoutRobot.isStateVisible(id.stateContent)
        StatefulLayoutRobot.isStateHidden(id.stateCustom)
        StatefulLayoutRobot.isStateHidden(id.stateError)
        StatefulLayoutRobot.isStateHidden(id.stateLoading)
    }

    fun isCustomStateDisplayed() {
        StatefulLayoutRobot.isStateVisible(id.stateCustom)
        StatefulLayoutRobot.isStateHidden(id.stateContent)
        StatefulLayoutRobot.isStateHidden(id.stateError)
        StatefulLayoutRobot.isStateHidden(id.stateLoading)
    }

    fun isErrorStateDisplayed() {
        StatefulLayoutRobot.isStateVisible(id.stateError)
        StatefulLayoutRobot.isStateHidden(id.stateContent)
        StatefulLayoutRobot.isStateHidden(id.stateCustom)
        StatefulLayoutRobot.isStateHidden(id.stateLoading)
    }

    fun isLoadingStateDisplayed() {
        StatefulLayoutRobot.isStateVisible(id.stateLoading)
        StatefulLayoutRobot.isStateHidden(id.stateContent)
        StatefulLayoutRobot.isStateHidden(id.stateCustom)
        StatefulLayoutRobot.isStateHidden(id.stateError)
    }
}
