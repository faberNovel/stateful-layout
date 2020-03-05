package com.stateful.statefullayout.transitions

import com.stateful.statefullayout.State

interface StateTransition : TransitionListenerHandler {
    fun start(state: State)
}

