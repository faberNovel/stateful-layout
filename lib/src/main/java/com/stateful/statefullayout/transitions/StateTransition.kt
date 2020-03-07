package com.stateful.statefullayout.transitions

import com.stateful.statefullayout.State

/**
 * Interface to play animation when [State] are shown and hidden.
 * @property start play the state transition
 */
interface StateTransition : TransitionListenerHandler {
    /**
     * Start the transition
     * - For enter transition, state visibility is set to visible on
     * [StateTransitionListener.onTransitionStart].
     * - For exit transition, state visibility is set to gone on
     * [StateTransitionListener.onTransitionEnd].
     * @param state [State] to play transition with.
     * @param listener [StateTransitionListener] used to update [State] visibility.
     */
    fun start(state: State, listener: StateTransitionListener)
}
