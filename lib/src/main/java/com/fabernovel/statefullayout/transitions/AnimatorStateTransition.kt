package com.fabernovel.statefullayout.transitions

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import androidx.annotation.AnimatorRes
import com.fabernovel.statefullayout.State

internal class AnimatorStateTransition(
    private val animator: Animator,
    private val handler: TransitionListenerHandler = DefaultTransitionListenerHandler()
) : StateTransition, TransitionListenerHandler by handler {

    constructor(
        context: Context,
        @AnimatorRes animatorRes: Int
    ) : this(AnimatorInflater.loadAnimator(context, animatorRes))

    init {
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                handler.dispatchTransitionRepeat(this@AnimatorStateTransition)
            }

            override fun onAnimationEnd(animation: Animator?) {
                handler.dispatchTransitionEnd(this@AnimatorStateTransition)
            }

            override fun onAnimationCancel(animation: Animator?) {
                handler.dispatchTransitionCancel(this@AnimatorStateTransition)
            }

            override fun onAnimationStart(animation: Animator?) {
                handler.dispatchTransitionStart(this@AnimatorStateTransition)
            }
        })
    }

    override fun start(state: State, listener: StateTransitionListener) {
        addListenerAndRemoveOnEnd(listener)
        animator.start()
    }

    override fun cancel() {
        animator.cancel()
    }
}
