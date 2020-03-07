package com.stateful.statefullayout.transitions

import com.stateful.statefullayout.State

internal typealias StateTransitionCallback = (StateTransition, StateTransitionListener) -> Unit

internal class CallbackStateTransition(
    private val stateTransition: StateTransitionCallback,
    private val transitionListenerHandler: TransitionListenerHandler = DefaultTransitionListenerHandler()
) : StateTransition, TransitionListenerHandler by transitionListenerHandler {
    override fun start(state: State, listener: StateTransitionListener) {
        stateTransition(this, addListenerAndRemoveOnEnd(listener))
    }
}
