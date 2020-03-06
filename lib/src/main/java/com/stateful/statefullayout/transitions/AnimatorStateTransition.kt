package com.stateful.statefullayout.transitions

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import androidx.annotation.AnimatorRes
import com.stateful.statefullayout.State

internal class AnimatorStateTransition(
    private val animator: Animator,
    private val transitionListenerHandler: TransitionListenerHandler = DefaultTransitionListenerHandler()
) : StateTransition, TransitionListenerHandler by transitionListenerHandler {

    constructor(
        context: Context,
        @AnimatorRes animatorRes: Int
    ) : this(AnimatorInflater.loadAnimator(context, animatorRes))

    init {
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                transitionListenerHandler.dispatchTransitionRepeat(this@AnimatorStateTransition)
            }

            override fun onAnimationEnd(animation: Animator?) {
                transitionListenerHandler.dispatchTransitionEnd(this@AnimatorStateTransition)
            }

            override fun onAnimationCancel(animation: Animator?) {
                transitionListenerHandler.dispatchTransitionCancel(this@AnimatorStateTransition)
            }

            override fun onAnimationStart(animation: Animator?) {
                transitionListenerHandler.dispatchTransitionStart(this@AnimatorStateTransition)
            }
        })
    }

    override fun start(state: State, onTransitionEnd: () -> Unit) {
        doOnEndOnce { onTransitionEnd() }
        animator.start()
    }
}
