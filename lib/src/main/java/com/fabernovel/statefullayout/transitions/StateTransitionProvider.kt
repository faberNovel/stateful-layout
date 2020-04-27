package com.fabernovel.statefullayout.transitions

interface StateTransitionProvider {
    fun get(): StateTransition
}
