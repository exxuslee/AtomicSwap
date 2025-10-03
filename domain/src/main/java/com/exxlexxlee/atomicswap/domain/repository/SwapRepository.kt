package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.domain.model.Swap
import kotlinx.coroutines.flow.Flow

interface SwapRepository {

    val swaps: Flow<List<Swap>>
    fun deleteAllHistory()
    fun swaps(): List<Swap>
    fun swap(id: String): Swap

}
