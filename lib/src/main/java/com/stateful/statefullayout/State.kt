package com.stateful.statefullayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.FrameLayout

class State : FrameLayout {
    lateinit var contentView: View
        private set

    private var enterAnim: Animation? = null
    private var exitAnim: Animation? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    fun setContentView(view: View) {
        removeAllViews()
        addView(view)
    }

    override fun addView(child: View?) {
        removeAllViews()
        super.addView(child)
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        if (child != null) {
            contentView = child
        }
    }

    fun show(fallbackAnimation: Animation? = null) {
        animateIfNeededThenUpdateVisibility(enterAnim, fallbackAnimation, View.VISIBLE)
    }

    fun hide(fallbackAnimation: Animation? = null) {
        animateIfNeededThenUpdateVisibility(exitAnim, fallbackAnimation, View.GONE)
    }

    private fun animateIfNeededThenUpdateVisibility(
        animation: Animation?,
        fallbackAnimation: Animation?,
        targetVisibility: Int
    ) {
        val appliedAnimation = animation ?: fallbackAnimation
        if (appliedAnimation != null) {
            appliedAnimation.setAnimationListener(object : AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                    /* no-op */
                }

                override fun onAnimationEnd(animation: Animation?) {
                    visibility = targetVisibility
                }

                override fun onAnimationStart(animation: Animation?) {
                    /* no-op */
                }
            })
            startAnimation(appliedAnimation)
        } else {
            visibility = targetVisibility
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            throw IllegalArgumentException("State can only contain one child. (found: $childCount)")
        }
    }
}
