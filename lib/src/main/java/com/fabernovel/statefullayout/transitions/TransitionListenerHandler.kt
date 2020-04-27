package com.fabernovel.statefullayout.transitions

/**
 * Dispatch [StateTransition] states to [StateTransitionListener]
 *
 */
interface TransitionListenerHandler {
    /**
     * Add a [StateTransitionListener] to dispatch actions to
     *
     * @param stateTransitionListener listener to add
     */
    fun addListener(stateTransitionListener: StateTransitionListener)
    /**
     * Remove a [StateTransitionListener]
     *
     * @param stateTransitionListener listener to remove
     */
    fun removeListener(stateTransitionListener: StateTransitionListener)

    /**
     * Remove all listeners
     *
     */
    fun clearListeners()

    /**
     * Dispatch transition repeat to listener
     *
     * @param transition repeated transition
     */
    fun dispatchTransitionRepeat(transition: StateTransition)

    /**
     * Dispatch transition end to listener
     *
     * @param transition ended transition
     */
    fun dispatchTransitionEnd(transition: StateTransition)
    /**
     * Dispatch transition cancel to listener
     *
     * @param transition cancelled transition
     */
    fun dispatchTransitionCancel(transition: StateTransition)
    /**
     * Dispatch transition start to listener
     *
     * @param transition started transition
     */
    fun dispatchTransitionStart(transition: StateTransition)
}
