package com.stateful.statefullayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.stateful.statefullayout.transitions.StateTransition
import com.stateful.statefullayout.transitions.StateTransitions

class State : FrameLayout {
    lateinit var contentView: View
        private set

    var enterAnimation: StateTransition? = null
    var exitAnimation: StateTransition? = null

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
                enterAnimation = StateTransitions.ofResource(context, enterAnimRes)
            }
            val exitAnimRes = array.getResourceId(R.styleable.State_exitAnimation, 0)
            if (exitAnimRes != 0) {
                exitAnimation = StateTransitions.ofResource(context, exitAnimRes)
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

    internal fun show(fallbackAnimation: StateTransition? = null) {
        animateIfNeededThenUpdateVisibility(enterAnimation ?: fallbackAnimation, View.VISIBLE)
    }

    internal fun hide(fallbackAnimation: StateTransition? = null) {
        animateIfNeededThenUpdateVisibility(exitAnimation ?: fallbackAnimation, View.GONE)
    }

    private fun animateIfNeededThenUpdateVisibility(
        animation: StateTransition?,
        targetVisibility: Int
    ) {
        if (animation != null) {
            animation.start(this) {
                visibility = targetVisibility
            }
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
