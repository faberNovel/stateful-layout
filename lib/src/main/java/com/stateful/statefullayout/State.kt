package com.stateful.statefullayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.stateful.statefullayout.transitions.DefaultStateTransitionListener
import com.stateful.statefullayout.transitions.StateTransition
import com.stateful.statefullayout.transitions.StateTransitions

class State : FrameLayout {
    lateinit var contentView: View
        private set

    var enterTransition: StateTransition? = null
    var exitTransition: StateTransition? = null

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
            val enterAnimRes = array.getResourceId(R.styleable.State_enterTransition, 0)

            if (enterAnimRes != 0) {
                enterTransition = StateTransitions.fromResource(context, enterAnimRes)
            }
            val exitAnimRes = array.getResourceId(R.styleable.State_exitTransition, 0)
            if (exitAnimRes != 0) {
                exitTransition = StateTransitions.fromResource(context, exitAnimRes)
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

    internal fun show(fallbackTransition: StateTransition? = null) {
        val transition = enterTransition ?: fallbackTransition
        if (transition == null) {
            visibility = View.VISIBLE
        } else {
            transition.start(this, object : DefaultStateTransitionListener {
                override fun onTransitionStart(transition: StateTransition) {
                    visibility = View.VISIBLE
                }
            })
        }
    }

    internal fun hide(fallbackTransition: StateTransition? = null) {
        val transition = exitTransition ?: fallbackTransition
        if (transition == null) {
            visibility = View.GONE
        } else {
            transition.start(this, object : DefaultStateTransitionListener {
                override fun onTransitionEnd(transition: StateTransition) {
                    visibility = View.GONE
                }
            })
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            throw IllegalArgumentException("State can only contain one child. (found: $childCount)")
        }
    }
}
