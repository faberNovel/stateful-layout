package com.fabernovel.statefullayout.transitions

interface TransitionListenerHandler {
    fun addListener(stateTransitionListener: StateTransitionListener)
    fun removeListener(stateTransitionListener: StateTransitionListener)
    fun clearListeners()

    fun dispatchTransitionRepeat(transition: StateTransition)
    fun dispatchTransitionEnd(transition: StateTransition)
    fun dispatchTransitionCancel(transition: StateTransition)
    fun dispatchTransitionStart(transition: StateTransition)
}
