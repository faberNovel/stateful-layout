package com.fabernovel.statefullayout.transitions

/**
 * A [StateTransition] listener
 *
 */
interface StateTransitionListener {
    /**
     * Transition repeat
     *
     * @param transition repeated transition
     */
    fun onTransitionRepeat(transition: StateTransition) {
        /* no-op */
    }
    /**
     * Transition end
     *
     * @param transition ended transition
     */
    fun onTransitionEnd(transition: StateTransition) {
        /* no-op */
    }
    /**
     * Transition cancel
     *
     * @param transition cancelled transition
     */
    fun onTransitionCancel(transition: StateTransition) {
        /* no-op */
    }
    /**
     * Transition start
     *
     * @param transition started transition
     */
    fun onTransitionStart(transition: StateTransition) {
        /* no-op */
    }
}
