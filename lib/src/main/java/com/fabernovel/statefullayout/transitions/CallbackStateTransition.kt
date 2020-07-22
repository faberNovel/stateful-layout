package com.fabernovel.statefullayout.transitions

import com.fabernovel.statefullayout.State

internal typealias StateTransitionCallback = (StateTransition, StateTransitionListener) -> Unit

internal class CallbackStateTransition(
    private val startTransition: StateTransitionCallback,
    private val onCancel: () -> Unit,
    private val handler: TransitionListenerHandler = DefaultTransitionListenerHandler()
) : StateTransition, TransitionListenerHandler by handler {
    override fun start(state: State, listener: StateTransitionListener) {
        startTransition(this, addListenerAndRemoveOnEnd(listener))
    }

    override fun cancel() {
        onCancel()
        handler.dispatchTransitionCancel(this)
    }
}
