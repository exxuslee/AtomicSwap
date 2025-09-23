package com.example.atomicswap.domain.repository

import com.example.atomicswap.domain.model.Swap
import com.example.atomicswap.domain.model.SwapType
import kotlinx.coroutines.flow.Flow

interface SwapRepository {
	suspend fun addSwap(type: SwapType, amount: Double, asset: String)
	fun observeHistory(): Flow<List<Swap>>
}
