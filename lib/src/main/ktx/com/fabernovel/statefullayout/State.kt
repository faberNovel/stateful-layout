package com.fabernovel.statefullayout

import android.view.View

/**
 * Convenient method to get [State.contentView] and require it to be not null.
 *
 * @return the state's content view
 * @throws [IllegalArgumentException] if state's content view is null
 */
fun State.requireContentView(): View {
    return requireNotNull(contentView) { "No content view was set for this state." }
}
