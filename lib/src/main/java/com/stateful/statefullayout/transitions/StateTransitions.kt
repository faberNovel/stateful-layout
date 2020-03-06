package com.stateful.statefullayout.transitions

import android.animation.Animator
import android.content.Context
import android.view.animation.Animation
import androidx.annotation.AnyRes

fun animatorStateTransition(animator: Animator) = StateTransitions.ofAnimator(animator)

fun animationStateTransition(animation: Animation) = StateTransitions.ofAnimation(animation)

fun resourceStateTransition(
    context: Context,
    @AnyRes res: Int
) = StateTransitions.ofResource(context, res)

fun stateTransition(transition: StateTransitionCallback) = StateTransitions.ofCallback(transition)

internal object StateTransitions {
    private const val ANIMATOR_RES = "animator"
    private const val ANIMATION_RES = "anim"

    fun ofResource(context: Context, @AnyRes res: Int): StateTransition {
        val resources = context.resources
        return when (val typeName = resources.getResourceTypeName(res)) {
            ANIMATOR_RES -> AnimatorStateTransition(context, res)
            ANIMATION_RES -> AnimationStateTransition(context, res)
            else -> throw IllegalArgumentException(
                "$typeName is not a valid resource type, use $ANIMATION_RES or $ANIMATOR_RES"
            )
        }
    }

    fun ofAnimator(animator: Animator): StateTransition = AnimatorStateTransition(animator)

    fun ofAnimation(animation: Animation): StateTransition = AnimationStateTransition(animation)

    fun ofCallback(stateTransition: StateTransitionCallback): StateTransition =
        CallbackStateTransition(stateTransition)
}
