package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Swap
import kotlinx.coroutines.flow.Flow

interface SwapRepository {

    val swaps: Flow<List<Swap>>
    suspend fun deleteAllHistory()
    fun swapsAll(): List<Swap>
    fun swap(id: String): Swap

}
