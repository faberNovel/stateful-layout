package com.fabernovel.statefullayout.transitions

import android.animation.Animator
import android.content.Context
import android.view.animation.Animation
import androidx.annotation.AnyRes
import com.fabernovel.statefullayout.transitions.StateTransitions.fromAnimation
import com.fabernovel.statefullayout.transitions.StateTransitions.fromAnimator
import com.fabernovel.statefullayout.transitions.StateTransitions.fromResource

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

    /**
     * Create a [StateTransition] using a [androidx.annotation.AnimRes] or
     * [androidx.annotation.AnimatorRes] resource
     *
     * @param context context used for inflation
     * @param res a [androidx.annotation.AnimRes] or [androidx.annotation.AnimatorRes] resource
     * @return a [StateTransition]
     */
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

    /**
     * Create a [StateTransition] using an [Animator]
     *
     * @param animator a [Animator]
     * @return a [StateTransition]
     */
    fun fromAnimator(animator: Animator): StateTransition = AnimatorStateTransition(animator)

    /**
     * Create a [StateTransition] using an [Animation]
     *
     * @param animation a [Animation]
     * @return a [StateTransition]
     */
    fun fromAnimation(animation: Animation): StateTransition = AnimationStateTransition(animation)

    /**
     * Create a [StateTransition] using a callback
     *
     * @param onStart `(StateTransition, StateTransitionListener) -> Unit` called on [StateTransition.start]
     * You *must* call [StateTransitionListener.onTransitionStart] and [StateTransitionListener.onTransitionEnd]
     * @param onCancel action to do on [StateTransition.cancel]
     * @return a [StateTransition]
     *
     * Usage:
     * ``` kotlin
     * val animator = AnimatorSet()
     * // add animators to the animator set
     * return StateTransitions.fromCallback(
     *      onStart = { transition, listener ->
     *          animator.addListener(
     *             // notify state transition about transition start
     *             onStart = { listener.onTransitionStart(transition) },
     *             // notify state transition about transition end
     *            onEnd = { listener.onTransitionEnd(transition) }
     *          )
     *          animator.start()
     *          },
     *      onCancel = animator::cancel
     *   )
     * ```
     */
    fun fromCallback(
        onStart: StateTransitionCallback,
        onCancel: () -> Unit
    ): StateTransition =
        CallbackStateTransition(onStart, onCancel)
}
