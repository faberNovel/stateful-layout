package com.stateful.statefullayout.transitions

import android.content.Context
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import com.stateful.statefullayout.State

internal class AnimationStateTransition(
    private val animation: Animation,
    private val transitionListenerHandler: TransitionListenerHandler = DefaultTransitionListenerHandler()
) : StateTransition, TransitionListenerHandler by transitionListenerHandler {

    constructor(
        context: Context,
        @AnimRes animRes: Int
    ) : this(AnimationUtils.loadAnimation(context, animRes))

    init {
        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                transitionListenerHandler.dispatchTransitionRepeat(this@AnimationStateTransition)
            }

            override fun onAnimationEnd(animation: Animation?) {
                transitionListenerHandler.dispatchTransitionEnd(this@AnimationStateTransition)
            }

            override fun onAnimationStart(animation: Animation?) {
                transitionListenerHandler.dispatchTransitionStart(this@AnimationStateTransition)
            }
        })
    }

    override fun start(state: State, listener: StateTransitionListener) {
        animation.reset()
        addListenerAndRemoveOnEnd(listener)
        state.startAnimation(animation)
    }

    override fun cancel() {
        animation.cancel()
    }
}
