package com.stateful.statefullayout.transitions

interface StateTransitionListener {
    fun onTransitionRepeat(transition: StateTransition)
    fun onTransitionEnd(transition: StateTransition)
    fun onTransitionCancel(transition: StateTransition)
    fun onTransitionStart(transition: StateTransition)
}
