package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.model.SwapType
import kotlinx.coroutines.flow.Flow

interface SwapRepository {
	suspend fun addSwap(type: SwapType, amount: Double, asset: String)
	fun observeHistory(): Flow<List<Swap>>
}
