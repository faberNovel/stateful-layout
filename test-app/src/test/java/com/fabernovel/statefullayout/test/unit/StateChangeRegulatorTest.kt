package com.fabernovel.statefullayout.test.unit

import com.fabernovel.statefullayout.StateChangeRegulator
import com.fabernovel.statefullayout.StateChangeRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class StateChangeRegulatorTest {
    @Test
    fun `should keep first requested state for at least minimalTimeBetweenRequests`() =
        runBlockingTest {
            var latestReceivedRequest: StateChangeRequest? = null
            val regulator = StateChangeRegulator(REGULATOR_PACE_MILLIS)

            val regulationJob = launch {
                regulator.handleStateChangeRequests { request ->
                    println("Processing $request")
                    latestReceivedRequest = request
                }
            }

            regulator.changeStateTo(0)
            delay(REGULATOR_PACE_MILLIS scaledTo .1)
            regulator.changeStateTo(1)
            regulator.changeStateTo(2)
            regulator.changeStateTo(3)
            delay(REGULATOR_PACE_MILLIS scaledTo .8)

            Assert.assertEquals(0, latestReceivedRequest?.id)
            regulationJob.cancel()
        }

    @Test
    fun `should only keep most recent request in case of backpressure`() = runBlockingTest {
        var latestReceivedRequest: StateChangeRequest? = null
        val regulator = StateChangeRegulator(REGULATOR_PACE_MILLIS)

        val regulationJob = launch {
            regulator.handleStateChangeRequests { request ->
                println("Processing $request")
                latestReceivedRequest = request
            }
        }

        regulator.changeStateTo(0)
        delay(REGULATOR_PACE_MILLIS scaledTo .5)
        regulator.changeStateTo(1)
        regulator.changeStateTo(2)
        delay(REGULATOR_PACE_MILLIS scaledTo .1)
        regulator.changeStateTo(3)
        delay(REGULATOR_PACE_MILLIS scaledTo 1.2)

        Assert.assertEquals(3, latestReceivedRequest?.id)
        regulationJob.cancel()
    }

    private suspend fun StateChangeRegulator.changeStateTo(stateId: Int) {
        println("Requesting state change to $stateId")
        requestStateChange(StateChangeRequest(stateId, false))
    }

    private infix fun Long.scaledTo(scale: Double): Long = (this * scale).toLong()

    companion object {
        private const val REGULATOR_PACE_MILLIS = 500L
    }
}
