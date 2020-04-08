package com.fabernovel.statefullayout

import android.view.View
import android.widget.Button
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

fun StatefulLayout.addStateView(@IdRes key: Int, view: View) {
    val state = State(context)
    state.id = key
    state.visibility = View.GONE
    state.setContentView(view)

    addView(state, 0)
}

fun StatefulLayout.addStateView(@IdRes key: Int, @LayoutRes layoutRes: Int) {
    val state = State(context)
    state.id = key
    state.visibility = View.GONE
    state.setContentView(layoutRes)

    addView(state, 0)
}

fun StatefulLayout.getStateView(@IdRes key: Int): View? {
    return this[key].contentView
}

fun StatefulLayout.requireStateView(@IdRes key: Int): View {
    return this[key].requireContentView()
}

fun StatefulLayout.showError(onRetryListener: ((View) -> Unit)? = null): State {
    val errorState = showState(R.id.stateError)
    if (onRetryListener != null) {
        val retryButton = errorState.findViewById<Button>(R.id.stateErrorRetryButton)
        requireNotNull(retryButton) {
            "The layout associated to the state 'stateError' must contain a Button with the id " +
            "'stateErrorRetryButton'"
        }

        retryButton.setOnClickListener(onRetryListener)
    }
    return errorState
}

fun StatefulLayout.showLoading(): State = showState(R.id.stateLoading)

fun StatefulLayout.showContent(): State = showState(R.id.stateContent)
