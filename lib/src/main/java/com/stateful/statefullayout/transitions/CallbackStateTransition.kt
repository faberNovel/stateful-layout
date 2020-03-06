package com.stateful.statefullayout.transitions

import com.stateful.statefullayout.State

typealias StartAnimationCallback = (onTransitionEnd: (State) -> Unit) -> Unit

class CallbackStateTransition(
    private val startAnimation: StartAnimationCallback,
    private val transitionListenerHandler: TransitionListenerHandler = DefaultTransitionListenerHandler()
) : StateTransition, TransitionListenerHandler by transitionListenerHandler {
    override fun start(state: State, onTransitionEnd: (State) -> Unit) = doOnEndOnce {
        startAnimation(onTransitionEnd)
    }
}
