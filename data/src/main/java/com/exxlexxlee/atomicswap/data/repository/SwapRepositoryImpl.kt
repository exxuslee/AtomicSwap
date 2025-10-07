package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.database.MakeDao
import com.exxlexxlee.atomicswap.core.database.SwapDao
import com.exxlexxlee.atomicswap.core.database.TakeDao
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.data.mapper.toDomain
import com.exxlexxlee.atomicswap.data.mapper.toEntity
import com.exxlexxlee.atomicswap.data.mapper.toMakeEntity
import com.exxlexxlee.atomicswap.data.mapper.toTakeEntity

import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

class SwapRepositoryImpl(
	private val swapDao: SwapDao,
	private val makeDao: MakeDao,
	private val takeDao: TakeDao,
): SwapRepository {

    override val swaps = swapDao.selectAll()
		.map { entities ->
			entities.map { swapEntity ->
				val takeEntity = takeDao.take(swapEntity.takeId)
				val makeEntity = makeDao.make(takeEntity.makeId)
				swapEntity.toDomain(makeEntity, takeEntity)
			}
		}
		.stateIn(
			scope = CoroutineScope(Dispatchers.IO),
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = emptyList()
		)

	override suspend fun deleteAllHistory() {
		swapDao.deleteHistory()
	}

	override fun swapsAll(): List<Swap> = run {
		val entities = runBlockingIO { swapDao.selectAll().first() }
		entities.map { swapEntity ->
			val takeEntity = runBlockingIO { takeDao.take(swapEntity.takeId) }
			val makeEntity = runBlockingIO { makeDao.make(takeEntity.makeId) }
			swapEntity.toDomain(makeEntity, takeEntity)
		}
	}

	override fun swap(id: String): Swap {
		val swapEntity = runBlockingIO { swapDao.selectById(id) }
		val takeEntity = runBlockingIO { takeDao.take(swapEntity.takeId) }
		val makeEntity = runBlockingIO { makeDao.make(takeEntity.makeId) }
		return swapEntity.toDomain(makeEntity, takeEntity)
	}

	suspend fun insertSwap(swap: Swap) = withContext(Dispatchers.IO) {
		val makeEntity = swap.take.make.toMakeEntity()
		val takeEntity = swap.take.toTakeEntity()
		val swapEntity = swap.toEntity()
		swapDao.insertAll(makeEntity, takeEntity, swapEntity)
	}

	suspend fun deleteSwap(id: String) = withContext(Dispatchers.IO) { swapDao.deleteById(id) }
}

private inline fun <T> runBlockingIO(crossinline block: suspend () -> T): T =
	kotlinx.coroutines.runBlocking(Dispatchers.IO) { block() }