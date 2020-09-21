package com.fabernovel.statefullayout.test.ui.error

import android.os.Build
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ErrorFragmentTest {

    @Test
    fun `when pressing show error with callback, error state should show with retry button`() {
        `on error fragment` {
            clickOnErrorWithCallbackButton()
            isErrorStateDisplayed()
            isRetryButtonVisible(shouldBeVisible = true)
        }
    }

    @Test
    fun `when pressing error without callback, error state should show without retry button`() {
        `on error fragment` {
            clickOnErrorWithoutCallbackButton()
            isErrorStateDisplayed()
            isRetryButtonVisible(shouldBeVisible = false)
        }
    }

    @Test
    fun `when pressing show content, content state should be displayed`() {
        `on error fragment` {
            clickOnContentButton()
            isContentStateDisplayed()
        }
    }
}
