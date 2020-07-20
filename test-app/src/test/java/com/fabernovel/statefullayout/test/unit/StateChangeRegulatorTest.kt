package com.fabernovel.statefullayout.test.unit

import com.fabernovel.statefullayout.StateChangeRegulator
import com.fabernovel.statefullayout.StateChangeRequest
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalCoroutinesApi
@FlowPreview
@ExperimentalTime
class StateChangeRegulatorTest {
    @Test
    fun `should keep first requested state for at least minimalTimeBetweenRequests`() {
        var latestReceivedRequest: StateChangeRequest? = null
        val regulator = StateChangeRegulator(REGULATOR_PACE) { request ->
            println("Received change request to $request at ${System.currentTimeMillis()}")
            latestReceivedRequest = request
        }

        GlobalScope.launch { regulator.start() }

        runBlocking {
            regulator.changeStateTo(0)
            delay(REGULATOR_PACE * 0.1)
            regulator.changeStateTo(1)
            regulator.changeStateTo(2)
            regulator.changeStateTo(3)
            delay(REGULATOR_PACE * 0.8)
            println("Asserting at ${System.currentTimeMillis()} that state is still 0")
            Assert.assertEquals(0, latestReceivedRequest?.id)
        }
    }

    @Test
    fun `should only keep most recent request in case of backpressure`() {
        var latestReceivedRequest: StateChangeRequest? = null
        val regulator = StateChangeRegulator(REGULATOR_PACE) { request ->
            println("Received change request to $request at ${System.currentTimeMillis()}")
            latestReceivedRequest = request
        }

        GlobalScope.launch { regulator.start() }

        runBlocking {
            regulator.changeStateTo(0)
            delay(REGULATOR_PACE * 0.5)
            regulator.changeStateTo(1)
            regulator.changeStateTo(2)
            delay(REGULATOR_PACE * 0.1)
            regulator.changeStateTo(3)
            delay(REGULATOR_PACE * 1.2)
            println("Asserting at ${System.currentTimeMillis()} that state is now 3")
            Assert.assertEquals(3, latestReceivedRequest?.id)
        }
    }

    private suspend fun StateChangeRegulator.changeStateTo(stateId: Int) {
        println("Requesting state change to $stateId at ${System.currentTimeMillis()}")
        requestStateChange(StateChangeRequest(stateId, false))
    }

    companion object {
        private val REGULATOR_PACE = 100.milliseconds
    }
}
