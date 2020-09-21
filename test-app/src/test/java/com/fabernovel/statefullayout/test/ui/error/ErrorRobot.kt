package com.fabernovel.statefullayout.test.ui.error

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.matcher.ViewMatchers
import com.fabernovel.statefullayout.test.R.id
import com.fabernovel.statefullayout.utils.clickOn
import com.fabernovel.statefullayout.utils.hasVisibility
import com.fabernovel.statefullayout.utils.isStateHidden
import com.fabernovel.statefullayout.utils.isStateVisible

fun `on error fragment`(func: ErrorRobot.() -> Unit) = ErrorRobot()
    .apply(func)

class ErrorRobot {
    init {
        FragmentScenario.launchInContainer(
            ErrorFragment::class.java
        )
    }

    fun clickOnContentButton() = clickOn(id.contentButton)

    fun clickOnErrorWithCallbackButton() = clickOn(id.errorWithCallbackButton)

    fun clickOnErrorWithoutCallbackButton() = clickOn(id.errorWithoutCallbackButton)

    fun isErrorStateDisplayed() {
        isStateHidden(id.stateContent)
        isStateVisible(id.stateError)
    }

    fun isContentStateDisplayed() {
        isStateVisible(id.stateContent)
        isStateHidden(id.stateError)
    }

    fun isRetryButtonVisible(shouldBeVisible: Boolean) {
        val expectedVisibility = if (shouldBeVisible) {
            ViewMatchers.Visibility.VISIBLE
        } else {
            ViewMatchers.Visibility.GONE
        }

        hasVisibility(id.stateErrorRetryButton, expectedVisibility)
    }
}
