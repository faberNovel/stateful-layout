package com.stateful.statefullayout.transitions

class DefaultTransitionListenerHandler : TransitionListenerHandler {
    private var listener: StateTransitionListener? = null

    override fun setListener(stateTransitionListener: StateTransitionListener?) {
        listener = stateTransitionListener
    }

    override fun onTransitionRepeat(transition: StateTransition) {
        listener?.onTransitionRepeat(transition)
    }

    override fun onTransitionEnd(transition: StateTransition) {
        listener?.onTransitionEnd(transition)
    }

    override fun onTransitionCancel(transition: StateTransition) {
        listener?.onTransitionCancel(transition)
    }

    override fun onTransitionStart(transition: StateTransition) {
        listener?.onTransitionStart(transition)
    }
}
