package com.stateful.statefullayout.transitions

interface DefaultStateTransitionListener : StateTransitionListener{
    override fun onTransitionRepeat(transition: StateTransition) {
        /* no-op */
    }
    override fun onTransitionEnd(transition: StateTransition) {
        /* no-op */
    }
    override fun onTransitionCancel(transition: StateTransition) {
        /* no-op */
    }
    override fun onTransitionStart(transition: StateTransition) {
        /* no-op */
    }
}
