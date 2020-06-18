package com.fabernovel.statefullayout.test.ui.main

import android.os.Build
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainFragmentTest {
    @Test
    fun `when pressing show error, error state should be displayed`() {
        `on main fragment` {
            clickOnErrorButton()
            isErrorStateDisplayed()
        }
    }

    @Test
    fun `when pressing show loading, loading state should be displayed`() {
        `on main fragment` {
            clickOnLoadingButton()
            isLoadingStateDisplayed()
        }
    }

    @Test
    fun `when pressing show content, content state should be displayed`() {
        `on main fragment` {
            clickOnContentButton()
            isContentStateDisplayed()
        }
    }

    @Test
    fun `when pressing show custom, custom state should be displayed`() {
        `on main fragment` {
            clickOnCustomButton()
            isCustomStateDisplayed()
        }
    }
}
