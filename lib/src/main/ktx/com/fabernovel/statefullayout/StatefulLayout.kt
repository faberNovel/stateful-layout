package com.fabernovel.statefullayout

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 * Add state view with a certain key.
 *
 * @param key the added state's id
 * @param view the state's [State.contentView]
 */
fun StatefulLayout.addStateView(@IdRes key: Int, view: View) {
    val state = State(context)
    state.id = key
    state.visibility = View.GONE
    state.setContentView(view)

    addView(state, 0)
}

/**
 * Add a state
 *
 * @param key the added state's id
 * @param layoutRes the state [State.contentView] to inflate.
 */
fun StatefulLayout.addStateView(@IdRes key: Int, @LayoutRes layoutRes: Int) {
    val state = State(context)
    state.id = key
    state.visibility = View.GONE
    state.setContentView(layoutRes)

    addView(state, 0)
}

/**
 * Convenient method to call [State.contentView] from a [StatefulLayout]
 *
 * @param key state's id
 * @return state's content view
 */
fun StatefulLayout.getStateView(@IdRes key: Int): View? {
    return this[key].contentView
}

/**
 * Convenient method to call [State.requireContentView] from a [StatefulLayout].
 *
 * @param key state's id
 * @return state's content view
 */
fun StatefulLayout.requireStateView(@IdRes key: Int): View {
    return this[key].requireContentView()
}

/**
 * Convenient method to show the built-in error state by calling [StatefulLayout.showState] with
 *  [R.id.stateError].
 * @param errorMessage if not null sets text of the TextView with id [R.id.stateErrorTitle]
 * @param onRetryListener if not null sets a [View.OnClickListener] on the button [R.id.stateErrorRetryButton]
 * @return the error state
 */
fun StatefulLayout.showError(
    errorMessage: String? = null,
    onRetryListener: ((View) -> Unit)? = null
): State {
    return showState(R.id.stateError).apply {
        if (errorMessage != null) setErrorMessage(errorMessage)
        if (onRetryListener != null) setErrorRetryListener(onRetryListener)
    }
}

private fun State.setErrorMessage(errorMessage: String) {
    val errorTextView = findViewById<TextView>(R.id.stateErrorTitle)
    requireNotNull(errorTextView) {
        "The layout associated to the state 'stateError' must contain a TextView with the id " +
            "'stateErrorTitle'"
    }

    errorTextView.text = errorMessage
}

private fun State.setErrorRetryListener(onRetryListener: (View) -> Unit) {
    val retryButton = findViewById<Button>(R.id.stateErrorRetryButton)
    requireNotNull(retryButton) {
        "The layout associated to the state 'stateError' must contain a Button with the id " +
            "'stateErrorRetryButton'"
    }

    retryButton.setOnClickListener(onRetryListener)
}

/**
 * Convenient method to show the built-in loading state by calling [StatefulLayout.showState] with
 *  [R.id.stateLoading].
 * @return the loading state
 */
fun StatefulLayout.showLoading(): State = showState(R.id.stateLoading)

/**
 * Convenient method to show the built-in content state by calling [StatefulLayout.showState] with
 *  [R.id.stateContent].
 * @return the content state
 */
fun StatefulLayout.showContent(): State = showState(R.id.stateContent)
