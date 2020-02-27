package com.stateful.statefullayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.IdRes

fun StatefulLayout.addStateView(@IdRes key: Int, view: View) {
    val state = State(context)
    state.id = key
    state.visibility = View.GONE
    state.setContentView(view)

    addView(state, 0)
}

fun StatefulLayout.inflateStateView(
    @IdRes key: Int,
    inflate: (inflater: LayoutInflater, parent: ViewGroup) -> View
) {
    addStateView(key, inflate(LayoutInflater.from(context), this))
}

fun StatefulLayout.getStateView(@IdRes key: Int): View {
    return this[key].contentView
}

fun StatefulLayout.showError(onRetryListener: (View) -> Unit = {}): State {
    val errorState = showState(R.id.stateError)
    errorState.findViewById<Button>(R.id.stateErrorRetryButton).setOnClickListener(onRetryListener)
    return errorState
}

fun StatefulLayout.showLoading(): State = showState(R.id.stateLoading)

fun StatefulLayout.showContent(): State = showState(R.id.stateContent)
