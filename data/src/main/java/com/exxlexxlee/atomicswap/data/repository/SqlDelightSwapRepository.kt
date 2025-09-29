package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.database.AppDatabase
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.model.SwapType
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SqlDelightSwapRepository(
	private val db: AppDatabase
) : SwapRepository {
	private val state = MutableStateFlow<List<Swap>>(emptyList())
	override suspend fun addSwap(type: SwapType, amount: Double, asset: String) {
		val typeStr = if (type == SwapType.TAKER) "taker" else "maker"
		db.historyQueries.insertItem(typeStr, amount, asset, System.currentTimeMillis())
		val items = db.historyQueries.getAll().executeAsList().map {
			Swap(
				id = it.id,
				type = if (it.type == "taker") SwapType.TAKER else SwapType.MAKER,
				amount = it.amount,
				asset = it.asset,
				timestamp = it.timestamp
			)
		}
		state.update { items }
	}
	override fun observeHistory(): Flow<List<Swap>> = state.asStateFlow()
}