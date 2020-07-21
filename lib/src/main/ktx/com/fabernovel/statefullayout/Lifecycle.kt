package com.fabernovel.statefullayout

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

internal val View.viewCoroutineScope: CoroutineScope
    get() {
        var viewScope = getTag(R.id.viewCoroutineScope) as CoroutineScope?

        if (viewScope == null) {
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {
                    setTag(R.id.viewCoroutineScope, null)
                    scope.cancel()
                }

                override fun onViewAttachedToWindow(v: View?) {
                    /* no-op */
                }
            })
            setTag(R.id.viewCoroutineScope, scope)
            viewScope = scope
        }

        return viewScope
    }
