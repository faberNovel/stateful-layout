package com.stateful.statefullayout.transitions

inline fun StateTransition.doOnEndOnce(crossinline action: (transition: StateTransition) -> Unit) {
    addListener(object : DefaultStateTransitionListener {
        override fun onTransitionEnd(transition: StateTransition) {
            action(transition)
            removeListener(this)
        }
    })
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
