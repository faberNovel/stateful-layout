package com.fabernovel.statefullayout.sample.ui.main

import androidx.fragment.app.testing.FragmentScenario
import com.fabernovel.statefullayout.sample.R.id
import com.fabernovel.statefullayout.utils.clickOn
import com.fabernovel.statefullayout.utils.isStateHidden
import com.fabernovel.statefullayout.utils.isStateVisible

fun `on main fragment`(func: MainRobot.() -> Unit) = MainRobot()
    .apply(func)

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
        isStateVisible(id.stateContent)
        isStateHidden(id.stateCustom)
        isStateHidden(id.stateError)
        isStateHidden(id.stateLoading)
    }

    fun isCustomStateDisplayed() {
        isStateVisible(id.stateCustom)
        isStateHidden(id.stateContent)
        isStateHidden(id.stateError)
        isStateHidden(id.stateLoading)
    }

    fun isErrorStateDisplayed() {
        isStateVisible(id.stateError)
        isStateHidden(id.stateContent)
        isStateHidden(id.stateCustom)
        isStateHidden(id.stateLoading)
    }

    fun isLoadingStateDisplayed() {
        isStateVisible(id.stateLoading)
        isStateHidden(id.stateContent)
        isStateHidden(id.stateCustom)
        isStateHidden(id.stateError)
    }
}
