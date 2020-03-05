package com.stateful.statefullayout.transitions

import android.content.Context
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import com.stateful.statefullayout.State

class AnimationStateTransition(
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
                Log.d("Transition","Animation: Animation repeat")
                transitionListenerHandler.onTransitionRepeat(this@AnimationStateTransition)
            }

            override fun onAnimationEnd(animation: Animation?) {
                Log.d("Transition","Animation: Animation end")
                transitionListenerHandler.onTransitionEnd(this@AnimationStateTransition)
            }

            override fun onAnimationStart(animation: Animation?) {
                Log.d("Transition","Animation: Animation start")
                transitionListenerHandler.onTransitionStart(this@AnimationStateTransition)
            }
        })
    }

    override fun start(state: State) {
        state.startAnimation(animation)
    }
}
