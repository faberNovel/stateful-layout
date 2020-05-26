package com.fabernovel.statefullayout

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.core.view.isVisible
import com.fabernovel.statefullayout.transitions.StateTransition
import com.fabernovel.statefullayout.transitions.StateTransitionProvider
import com.fabernovel.statefullayout.transitions.StateTransitions

/**
 * A layout containing states ([State]). Only one state is displayed at a time.
 */
class StatefulLayout : FrameLayout, StateContainer<Int, State> {
    @IdRes private var initialStateId: Int = View.NO_ID

    private val states: MutableMap<Int, State> = mutableMapOf()

    @IdRes private var _currentStateId: Int = View.NO_ID
    override val currentStateId: Int
        get() = _currentStateId

    /**
     *  [StateTransition] played when any state is displayed
     */
    var defaultEnterTransition: StateTransitionProvider? = null

    /**
     * [StateTransition] played when any state is hidden
     */
    var defaultExitTransition: StateTransitionProvider? = null

    /**
     * If disabled no [StateTransition] will be played
     */
    var areTransitionsEnabled: Boolean = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.statefulLayoutStyle
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defAttrRes: Int
    ) : this(context, attrs, defAttrRes, R.style.Widget_Stateful_StatefulLayout)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defAttrRes: Int,
        @StyleRes defStyleRes: Int
    ) : super(context, attrs, defAttrRes, defStyleRes) {
        init(context, attrs, defAttrRes, defStyleRes)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defAttrRes: Int,
        @StyleRes defStyleRes: Int
    ) {
        isSaveEnabled = true

        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatefulLayout,
            defAttrRes,
            defStyleRes
        )
        try {
            inflateLoadingState(array)
            inflateErrorState(array)

            loadDefaultTransitions(array)

            initialStateId = array.getResourceId(
                R.styleable.StatefulLayout_initialState,
                View.NO_ID
            )
        } finally {
            array.recycle()
        }
    }

    private fun loadDefaultTransitions(array: TypedArray) {
        val areTransitionsEnabled = array.getBoolean(
            R.styleable.StatefulLayout_areTransitionsEnabled,
            true
        )
        this.areTransitionsEnabled = areTransitionsEnabled

        val defaultEnterAnimRes = array.getResourceId(
            R.styleable.StatefulLayout_defaultEnterTransition,
            0
        )
        if (defaultEnterAnimRes != 0) {
            defaultEnterTransition = createTransitionProviderOf(defaultEnterAnimRes)
        }
        val defaultExitAnimRes = array.getResourceId(
            R.styleable.StatefulLayout_defaultExitTransition,
            0
        )
        if (defaultExitAnimRes != 0) {
            defaultExitTransition = createTransitionProviderOf(defaultExitAnimRes)
        }
    }

    private fun createTransitionProviderOf(animRes: Int): StateTransitionProvider {
        return object : StateTransitionProvider {
            override fun get(): StateTransition {
                return StateTransitions.fromResource(context, animRes)
            }
        }
    }

    private fun inflateLoadingState(array: TypedArray) {
        val loadingLayout = array.getResourceId(
            R.styleable.StatefulLayout_loadingStateLayout,
            R.layout.state_loading
        )
        addStateView(R.id.stateLoading, loadingLayout)
    }

    private fun inflateErrorState(array: TypedArray) {
        val errorLayout = array.getResourceId(
            R.styleable.StatefulLayout_errorStateLayout,
            R.layout.state_error
        )
        addStateView(R.id.stateError, errorLayout)
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        child?.isVisible = child?.id == initialStateId
        if (child !is State) {
            throw IllegalArgumentException("StatefulLayout child must be a State. ($child)")
        }

        val childId = child.id
        if (childId == View.NO_ID) {
            throw IllegalArgumentException("StatefulLayout states should have an id. ($child)")
        }
        removeExistingStateView(childId)
        child.isVisible = childId == initialStateId
        states[childId] = child
    }

    private fun removeExistingStateView(@IdRes stateId: Int) {
        val existingStateView = states[stateId]
        if (existingStateView != null) {
            removeView(existingStateView)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        states.forEach { (id, state) ->
            state.isVisible = id == initialStateId
        }
        _currentStateId = initialStateId
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.currentStateId = _currentStateId
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState?
        super.onRestoreInstanceState(state?.superState)

        if (savedState != null) {
            if (savedState.currentStateId != View.NO_ID) {
                showState(savedState.currentStateId)
            }
        }
    }

    /**
     * Show a state.
     *
     * Note:
     *  If [areTransitionsEnabled] is disabled, no transition will be played.
     *
     * @param id state's id
     * @return shown state
     * @throws [NoSuchElementException] if [id] was not found.
     */
    override fun showState(@IdRes id: Int): State {
        return showState(id, areTransitionsEnabled)
    }

    /**
     * Show a state.
     *
     * @param id
     * @param showTransitions if true, the transition will be played
     *  (overrides [areTransitionsEnabled])
     * @return
     */
    fun showState(@IdRes id: Int, showTransitions: Boolean): State {
        if (currentStateId != View.NO_ID) {
            val currentState = get(currentStateId)
            if (id == currentStateId) {
                return currentState
            }
            currentState.hide(defaultExitTransition, showTransitions)
        }

        val nextState = states[id]
            ?: throw NoSuchElementException("$id was not found in this StatefulLayout.")
        nextState.show(defaultEnterTransition, showTransitions)

        _currentStateId = id
        return nextState
    }

    /**
     * Add a state to the stateful layout
     * If the id already exists, the previous state is override.
     *
     * @param id state id
     * @param state state to add
     */
    override fun addState(@IdRes id: Int, state: State) {
        state.id = id
        addView(state, 0)
    }

    /**
     * Get a state by it's id
     *
     * @param id the id of the state to get
     * @return a state
     * @throws [NoSuchElementException] if the state was not found.
     */
    override fun get(id: Int): State {
        return states[id]
            ?: throw NoSuchElementException("$id was not found in this StatefulLayout.")
    }

    private class SavedState : BaseSavedState {
        var currentStateId: Int = View.NO_ID

        constructor(superState: Parcelable?) : super(superState)

        private constructor(savedState: Parcel) : super(savedState) {
            currentStateId = savedState.readInt()
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeInt(currentStateId)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel) = SavedState(source)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }
}
