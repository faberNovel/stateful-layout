package com.stateful.statefullayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.FrameLayout

class State : FrameLayout {
    lateinit var contentView: View
        private set

    private var enterAnimation: Animation? = null
    private var exitAnimation: Animation? = null

    constructor(context: Context) : this(context, null)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, R.style.Widget_Stateful_State)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : this(context, attrs, defStyle, R.attr.stateStyle)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyle, defStyleRes) {
        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.State,
            defStyle,
            defStyleRes
        )
        try {
            val enterAnimRes = array.getResourceId(R.styleable.State_enterAnimation, 0)

            if (enterAnimRes != 0) {
                enterAnimation = AnimationUtils.loadAnimation(context, enterAnimRes)
            }
            val exitAnimRes = array.getResourceId(R.styleable.State_exitAnimation, 0)
            if (exitAnimRes != 0) {
                exitAnimation = AnimationUtils.loadAnimation(context, exitAnimRes)
            }
        } finally {
            array.recycle()
        }
    }

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
        animateIfNeededThenUpdateVisibility(enterAnimation, fallbackAnimation, View.VISIBLE)
    }

    fun hide(fallbackAnimation: Animation? = null) {
        animateIfNeededThenUpdateVisibility(exitAnimation, fallbackAnimation, View.GONE)
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
