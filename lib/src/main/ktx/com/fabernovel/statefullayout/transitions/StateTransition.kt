package com.fabernovel.statefullayout.transitions

/**
 * Add a [StateTransitionListener] on a [StateTransition] and remove it on transition end.
 *
 * @param listener
 * @return
 */
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

/**
 * Add do [action] on [StateTransition]'s end
 *
 * @param action action to do
 */
inline fun StateTransition.doOnEnd(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onEnd = action)

/**
 * Add do [action] on [StateTransition]'s start
 *
 * @param action action to do
 */
inline fun StateTransition.doOnStart(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onStart = action)

/**
 * Add do [action] on [StateTransition]'s cancel
 *
 * @param action action to do
 */
inline fun StateTransition.doOnCancel(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onCancel = action)

/**
 * Add do [action] on [StateTransition]'s repeat
 *
 * @param action action to do
 */
inline fun StateTransition.doOnRepeat(crossinline action: (transition: StateTransition) -> Unit) =
    setListener(onRepeat = action)

/**
 * Convenient method to set a [StateTransitionListener]
 *
 * @param onEnd action on end
 * @param onStart action on start
 * @param onCancel action on cancel
 * @param onRepeat action on repeat
 * @return the set [StateTransitionListener]
 */
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
