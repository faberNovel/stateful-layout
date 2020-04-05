package com.stateful.statefullayout.transitions

inline fun StateTransition.addListenerAndRemoveOnEnd(
    listener: StateTransitionListener
): StateTransitionListener {
    val stateTransitionListener = object : StateTransitionListener {
        override fun onTransitionRepeat(transition: StateTransition) {
            listener.onTransitionRepeat(transition)
        }

        override fun onTransitionEnd(transition: StateTransition) {
            listener.onTransitionEnd(transition)
            removeListener(this)
        }

        override fun onTransitionCancel(transition: StateTransition) {
            listener.onTransitionCancel(transition)
        }

        override fun onTransitionStart(transition: StateTransition) {
            listener.onTransitionStart(transition)
        }
    }
    addListener(stateTransitionListener)
    return stateTransitionListener
}

inline fun StateTransition.doOnEnd(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onEnd = action)

inline fun StateTransition.doOnStart(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onStart = action)

inline fun StateTransition.doOnCancel(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onCancel = action)

inline fun StateTransition.doOnRepeat(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onRepeat = action)

inline fun StateTransition.setListener(
    crossinline onEnd: (transition: StateTransition) -> Unit = {},
    crossinline onStart: (transition: StateTransition) -> Unit = {},
    crossinline onCancel: (transition: StateTransition) -> Unit = {},
    crossinline onRepeat: (transition: StateTransition) -> Unit = {}
): StateTransitionListener {
    val listener = object : StateTransitionListener {
        override fun onTransitionRepeat(transition: StateTransition) = onRepeat(transition)
        override fun onTransitionEnd(transition: StateTransition) = onEnd(transition)
        override fun onTransitionCancel(transition: StateTransition) = onCancel(transition)
        override fun onTransitionStart(transition: StateTransition) = onStart(transition)
    }
    addListener(listener)
    return listener
}
