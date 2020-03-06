package com.stateful.statefullayout.transitions

class DefaultTransitionListenerHandler : TransitionListenerHandler {
    private val listeners: MutableList<StateTransitionListener> = mutableListOf()

    override fun addListener(stateTransitionListener: StateTransitionListener) {
        listeners.add(stateTransitionListener)
    }

    override fun removeListener(stateTransitionListener: StateTransitionListener) {
        listeners.remove(stateTransitionListener)
    }

    override fun clearListeners() {
        listeners.clear()
    }

    override fun dispatchTransitionRepeat(transition: StateTransition) {
        listeners.onEach { listener -> listener.onTransitionRepeat(transition) }
    }

    override fun dispatchTransitionEnd(transition: StateTransition) {
        listeners.onEach { listener -> listener.onTransitionEnd(transition) }
    }

    override fun dispatchTransitionCancel(transition: StateTransition) {
        listeners.onEach { listener -> listener.onTransitionCancel(transition) }
    }

    override fun dispatchTransitionStart(transition: StateTransition) {
        listeners.onEach { listener -> listener.onTransitionStart(transition) }
    }
}
