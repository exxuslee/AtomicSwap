package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import kotlinx.coroutines.flow.Flow

interface MakeRepository {

    val makes: Flow<List<Make>>
    fun make(makeId: String): Make
    fun myMake(makerId: String): List<Make>

}
