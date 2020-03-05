package com.stateful.statefullayout.transitions

interface TransitionListenerHandler : StateTransitionListener {
    fun setListener(stateTransitionListener: StateTransitionListener?)
}
