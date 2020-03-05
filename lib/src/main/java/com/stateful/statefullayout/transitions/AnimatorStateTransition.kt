package com.stateful.statefullayout.transitions

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import androidx.annotation.AnimatorRes
import androidx.core.animation.addListener
import com.stateful.statefullayout.State

class AnimatorStateTransition(
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
                transitionListenerHandler.onTransitionRepeat(this@AnimatorStateTransition)
            }

            override fun onAnimationEnd(animation: Animator?) {
                transitionListenerHandler.onTransitionEnd(this@AnimatorStateTransition)
            }

            override fun onAnimationCancel(animation: Animator?) {
                transitionListenerHandler.onTransitionCancel(this@AnimatorStateTransition)
            }

            override fun onAnimationStart(animation: Animator?) {
                transitionListenerHandler.onTransitionStart(this@AnimatorStateTransition)
            }
        })
    }

    override fun start(state: State) {
        animator.start()
    }
}
