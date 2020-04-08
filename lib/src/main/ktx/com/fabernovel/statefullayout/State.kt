package com.fabernovel.statefullayout

import android.view.View

fun State.requireContentView(): View {
    return requireNotNull(contentView) { "No content view was set for this state." }
}
