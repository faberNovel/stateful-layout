package com.fabernovel.statefullayout.test.ui.override

import android.os.Build
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class OverrideDefaultFragmentTest {

    @Test
    fun `given a stateful with only overridden defaults states, it should contains only two states`() {
        `on override default fragment` {
            containsOnlyOverrideDefaultStates()
            hasOverriddenErrorState()
            hasOverriddenLoadingState()
        }
    }
}
