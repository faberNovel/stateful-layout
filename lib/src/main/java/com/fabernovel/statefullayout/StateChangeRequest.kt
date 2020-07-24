package com.fabernovel.statefullayout

import androidx.annotation.IdRes
import androidx.annotation.VisibleForTesting

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
data class StateChangeRequest(
    @IdRes val id: Int,
    val showTransitions: Boolean
)
