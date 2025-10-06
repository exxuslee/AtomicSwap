package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.database.SwapDao
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
    private val dao: SwapDao,
): SwapRepository {

    override val swaps = dao.selectAll()
		.map { entities ->
			entities.map { swapEntity ->
				val makeEntity = dao.selectMakeById(swapEntity.makeId)
				val takeEntity = if (swapEntity.takeId != null) {
					dao.selectTakeById(swapEntity.takeId!!)
				} else {
					dao.selectTakesByMakeId(swapEntity.makeId).first()
				}
				swapEntity.toDomain(makeEntity, takeEntity)
			}
		}
		.stateIn(
			scope = CoroutineScope(Dispatchers.IO),
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = emptyList()
		)

	override suspend fun deleteAllHistory() {
		dao.deleteHistory()
	}

	override fun swapsAll(): List<Swap> = run {
		val entities = runBlockingIO { dao.selectAll().first() }
		entities.map { swapEntity ->
			val makeEntity = runBlockingIO { dao.selectMakeById(swapEntity.makeId) }
			val takeEntity = if (swapEntity.takeId != null) {
				runBlockingIO { dao.selectTakeById(swapEntity.takeId!!) }
			} else {
				runBlockingIO { dao.selectTakesByMakeId(swapEntity.makeId) }.first()
			}
			swapEntity.toDomain(makeEntity, takeEntity)
		}
	}

	override fun swap(id: String): Swap {
		val swapEntity = runBlockingIO { dao.selectById(id) }
		val makeEntity = runBlockingIO { dao.selectMakeById(swapEntity.makeId) }
		val takeEntity = if (swapEntity.takeId != null) {
			runBlockingIO { dao.selectTakeById(swapEntity.takeId!!) }
		} else {
			runBlockingIO { dao.selectTakesByMakeId(swapEntity.makeId) }.first()
		}
		return swapEntity.toDomain(makeEntity, takeEntity)
	}

	suspend fun insertSwap(swap: Swap) = withContext(Dispatchers.IO) {
		val makeEntity = swap.take.make.toMakeEntity()
		val takeEntity = swap.take.toTakeEntity()
		val swapEntity = swap.toEntity()
		dao.insertAll(makeEntity, takeEntity, swapEntity)
	}

	suspend fun deleteSwap(id: String) = withContext(Dispatchers.IO) { dao.deleteById(id) }
}

private inline fun <T> runBlockingIO(crossinline block: suspend () -> T): T =
	kotlinx.coroutines.runBlocking(Dispatchers.IO) { block() }