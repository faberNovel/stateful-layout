package com.fabernovel.statefullayout.sample.ui.empty

import android.os.Build
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class EmptyFragmentTest {

    @Test
    fun `given a stateful with no state, it should contains only two states`() {
        `on empty fragment` {
            containsOnlyDefaultStates()
        }
    }

    @Test
    fun `given a stateful layout has no initial state, no state should be visible`() {
        `on empty fragment` {
            areAllStateGone()
        }
    }
}
