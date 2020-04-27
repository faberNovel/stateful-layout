package com.fabernovel.statefullayout

/**
 * A container of [StateT] referenced by [IdT]
 *
 * @param IdT states' id
 * @param StateT state type
 */
interface StateContainer<IdT, StateT> {
    /**
     * Currently displayed [StateT]'s [IdT]
     */
    val currentStateId: IdT

    /**
     * Show a state
     *
     * @param id state's [IdT]
     * @return displayed [StateT]
     */
    fun showState(id: IdT): StateT

    /**
     * Add a [StateT] to this [StateContainer]
     *
     * @param id added state's [IdT]
     * @param state [StateT] to add
     */
    fun addState(id: IdT, state: StateT)

    /**
     * Get a [StateT] by it's [IdT]
     *
     * @param id state's [IdT]
     * @return the state
     */
    operator fun get(id: IdT): State
}
