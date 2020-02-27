package com.stateful.statefullayout

interface StateContainer<IdT, StateT> {
    val currentStateId: IdT

    fun showState(id: IdT): StateT
    fun addState(id: IdT, state: StateT)

    operator fun get(id: IdT): State
}
