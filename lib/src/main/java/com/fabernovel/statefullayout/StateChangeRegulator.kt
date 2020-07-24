package com.fabernovel.statefullayout

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

/**
 * Responsible for regulating state-change requests.
 *
 * Use in conjunction with [StatefulLayout] for a "minimal duration by state"  behavior.
 *
 *
 * Will make sure that once [handleStateChangeRequests] is called, some or all requests passed to
 * [requestStateChange] method will be forwarded to requestHandler, respecting the minimal time between
 * requests [minimalTimeBetweenRequestsMillis].
 *
 * If requests are chained (almost zero milliseconds in between) then all will be ignored except for
 * the last one.
 *
 * If one or many requests are sent during the [minimalTimeBetweenRequestsMillis] after a request has been
 * handled then only the most recent one will be forwarded to the handler right after the minimal
 * time has elapsed.
 *
 * Please note that due to platform limitations the effective time will not always match
 * the exact expected value in milliseconds.
 */
@FlowPreview
@ExperimentalCoroutinesApi
@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
class StateChangeRegulator(
    var minimalTimeBetweenRequestsMillis: Long
) {
    private val pendingRequests: BroadcastChannel<StateChangeRequest> = ConflatedBroadcastChannel()
    private val processedRequests = pendingRequests.asFlow()
        .flatMapConcat {
            flow {
                emit(it)
                delay(minimalTimeBetweenRequestsMillis)
            }
        }

    /**
     * Queue requests to be regulated before handled.
     */
    suspend fun requestStateChange(request: StateChangeRequest) {
        pendingRequests.send(request)
    }

    /**
     * Start regulating requests. To be called from a dedicated coroutine.
     */
    suspend fun handleStateChangeRequests(requestHandler: (StateChangeRequest) -> Unit) {
        processedRequests.collect { request -> requestHandler(request) }
    }
}
