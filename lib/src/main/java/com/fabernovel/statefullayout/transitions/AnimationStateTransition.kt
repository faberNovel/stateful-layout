package com.fabernovel.statefullayout.transitions

import android.content.Context
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import com.fabernovel.statefullayout.State

internal class AnimationStateTransition(
    private val animation: Animation,
    private val handler: TransitionListenerHandler = DefaultTransitionListenerHandler()
) : StateTransition, TransitionListenerHandler by handler {

    constructor(
        context: Context,
        @AnimRes animRes: Int
    ) : this(AnimationUtils.loadAnimation(context, animRes))

    init {
        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                handler.dispatchTransitionRepeat(this@AnimationStateTransition)
            }

            override fun onAnimationEnd(animation: Animation?) {
                handler.dispatchTransitionEnd(this@AnimationStateTransition)
            }

            override fun onAnimationStart(animation: Animation?) {
                handler.dispatchTransitionStart(this@AnimationStateTransition)
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
