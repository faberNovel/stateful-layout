package com.stateful.statefullayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.annotation.AnimRes

class State : FrameLayout {
    lateinit var contentView: View
        private set

    @AnimRes private var enterAnim: Int = 0
    @AnimRes private var exitAnim: Int = 0

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

    fun show(@AnimRes defaultEnterAnimRes: Int = 0) {
        animateIfNeededThenUpdateVisibility(enterAnim, defaultEnterAnimRes, View.VISIBLE)
    }

    fun hide(@AnimRes defaultExitAnimRes: Int = 0) {
        animateIfNeededThenUpdateVisibility(exitAnim, defaultExitAnimRes, View.GONE)
    }

    private fun animateIfNeededThenUpdateVisibility(
        @AnimRes animRes: Int,
        @AnimRes fallbackAnimRes: Int,
        targetVisibility: Int
    ) {
        val appliedAnimRes = if (animRes != 0) animRes else fallbackAnimRes
        if (appliedAnimRes != 0) {
            val animation = AnimationUtils.loadAnimation(context, appliedAnimRes)
            animation.setAnimationListener(object : AnimationListener {
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
            startAnimation(animation)
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
