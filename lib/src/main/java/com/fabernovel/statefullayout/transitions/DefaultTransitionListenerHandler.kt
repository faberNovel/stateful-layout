package com.fabernovel.statefullayout.transitions

internal class DefaultTransitionListenerHandler : TransitionListenerHandler {
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
        val currentListeners = ArrayList(listeners)
        for (listener in currentListeners) {
            listener.onTransitionRepeat(transition)
        }
    }

    override fun dispatchTransitionEnd(transition: StateTransition) {
        val currentListeners = ArrayList(listeners)
        for (listener in currentListeners) {
            listener.onTransitionEnd(transition)
        }
    }

    override fun dispatchTransitionCancel(transition: StateTransition) {
        val currentListeners = ArrayList(listeners)
        for (listener in currentListeners) {
            listener.onTransitionCancel(transition)
        }
    }

    override fun dispatchTransitionStart(transition: StateTransition) {
        val currentListeners = ArrayList(listeners)
        for (listener in currentListeners) {
            listener.onTransitionStart(transition)
        }
    }
}
