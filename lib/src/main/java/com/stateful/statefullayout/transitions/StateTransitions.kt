package com.stateful.statefullayout.transitions

import android.animation.Animator
import android.content.Context
import android.view.animation.Animation
import androidx.annotation.AnyRes
import com.stateful.statefullayout.transitions.StateTransitions.fromAnimation
import com.stateful.statefullayout.transitions.StateTransitions.fromAnimator
import com.stateful.statefullayout.transitions.StateTransitions.fromResource

/**
 * Helper object to create state transitions
 * @property fromResource create a [StateTransition] from a [androidx.annotation.AnimatorRes]
 * or [androidx.annotation.AnimRes] resource.
 * @property fromAnimation create a state transition from an [Animation].
 * @property fromAnimator create a state transition from an [Animator].
 */
object StateTransitions {
    private const val ANIMATOR_RES = "animator"
    private const val ANIMATION_RES = "anim"

    fun fromResource(context: Context, @AnyRes res: Int): StateTransition {
        val resources = context.resources
        return when (val typeName = resources.getResourceTypeName(res)) {
            ANIMATOR_RES -> AnimatorStateTransition(context, res)
            ANIMATION_RES -> AnimationStateTransition(context, res)
            else -> throw IllegalArgumentException(
                "$typeName is not a valid resource type, use $ANIMATION_RES or $ANIMATOR_RES"
            )
        }
    }

    fun fromAnimator(animator: Animator): StateTransition = AnimatorStateTransition(animator)

    fun fromAnimation(animation: Animation): StateTransition = AnimationStateTransition(animation)

    fun fromCallback(stateTransition: StateTransitionCallback): StateTransition =
        CallbackStateTransition(stateTransition)
}
