package com.exxlexxlee.atomicswap.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.exxlexxlee.atomicswap.core.database.AppDatabase
import com.exxlexxlee.atomicswap.data.mapper.toDomain
import com.exxlexxlee.atomicswap.data.mapper.toEntity
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

class SwapRepositoryImpl(
	db: AppDatabase,
) : SwapRepository {

	private val queries = db.swapQueries

	override val swaps = queries.selectAll()
		.asFlow()
		.mapToList(Dispatchers.IO)
		.map { entities -> entities.map { it.toDomain() } }
		.stateIn(
			scope = CoroutineScope(Dispatchers.IO),
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = emptyList()
		)

	override fun deleteAllHistory() {
		queries.deleteHistory()
	}

	override fun swaps(): List<Swap> {
		return queries.selectAll().executeAsList().map { it.toDomain() }
	}

	override fun swap(id: String): Swap {
		return queries.selectById(id).executeAsOne().toDomain()
	}

	suspend fun insertSwap(swap: Swap) = withContext(Dispatchers.IO) {
		queries.insert(swap.toEntity())
	}

	suspend fun deleteSwap(id: String) = withContext(Dispatchers.IO) {
		queries.deleteById(id)
	}

}