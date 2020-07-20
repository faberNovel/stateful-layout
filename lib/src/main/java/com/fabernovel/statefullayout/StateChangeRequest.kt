package com.fabernovel.statefullayout

import androidx.annotation.IdRes

data class StateChangeRequest(
    @IdRes val id: Int,
    val showTransitions: Boolean
)
