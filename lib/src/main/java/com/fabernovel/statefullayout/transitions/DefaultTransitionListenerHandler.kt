package com.fabernovel.statefullayout.transitions

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
        for (listener in listeners) {
            listener.onTransitionRepeat(transition)
        }
    }

    override fun dispatchTransitionEnd(transition: StateTransition) {
        for (listener in listeners) {
            listener.onTransitionEnd(transition)
        }
    }

    override fun dispatchTransitionCancel(transition: StateTransition) {
        for (listener in listeners) {
            listener.onTransitionCancel(transition)
        }
    }

    override fun dispatchTransitionStart(transition: StateTransition) {
        for (listener in listeners) {
            listener.onTransitionStart(transition)
        }
    }
}
