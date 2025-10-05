package com.exxlexxlee.atomicswap.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.exxlexxlee.atomicswap.core.database.AppDatabase
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.data.mapper.toDomain
import com.exxlexxlee.atomicswap.data.mapper.toEntity
import com.exxlexxlee.atomicswap.data.mapper.toMakeEntity
import com.exxlexxlee.atomicswap.data.mapper.toTakeEntity
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
		.map { entities ->
			entities.map { swapEntity ->
				val makeEntity = queries.selectMakeById(swapEntity.makeId).executeAsOne()
				val takeEntities = queries.selectTakesByMakeId(swapEntity.makeId).executeAsList()
				swapEntity.toDomain(makeEntity, takeEntities)
			}
		}
		.stateIn(
			scope = CoroutineScope(Dispatchers.IO),
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = emptyList()
		)

	override fun deleteAllHistory() {
		queries.deleteHistory()
	}

	override fun swapsAll(): List<Swap> =
		queries.selectAll().executeAsList().map { swapEntity ->
			val makeEntity = queries.selectMakeById(swapEntity.makeId).executeAsOne()
			val takeEntities = queries.selectTakesByMakeId(swapEntity.makeId).executeAsList()
			swapEntity.toDomain(makeEntity, takeEntities)
		}

	override fun swap(id: String): Swap {
		val swapEntity = queries.selectByIdFull(id).executeAsOne()
		val makeEntity = queries.selectMakeById(swapEntity.makeId).executeAsOne()
		val takeEntities = queries.selectTakesByMakeId(swapEntity.makeId).executeAsList()
		return swapEntity.toDomain(makeEntity, takeEntities)
	}

	suspend fun insertSwap(swap: Swap) = withContext(Dispatchers.IO) {
		// Insert Make entity
		val makeEntity = swap.take.make.toMakeEntity()
		queries.insertMake(
			makeId = makeEntity.makeId,
			makerId = makeEntity.makerId,
			makerRefundAddress = makeEntity.makerRefundAddress,
			makerRedeemAddress = makeEntity.makerRedeemAddress,
			makerExactAmount = makeEntity.makerExactAmount,
			takerExactAmount = makeEntity.takerExactAmount,
			makerStartAmount = makeEntity.makerStartAmount,
			takerStartAmount = makeEntity.takerStartAmount,
			makerTokenCoinId = makeEntity.makerTokenCoinId,
			makerTokenCoinSymbol = makeEntity.makerTokenCoinSymbol,
			makerTokenCoinName = makeEntity.makerTokenCoinName,
			makerTokenCoinIconUrl = makeEntity.makerTokenCoinIconUrl,
			makerTokenContractAddress = makeEntity.makerTokenContractAddress,
			makerTokenBlockchain = makeEntity.makerTokenBlockchain,
			makerTokenDecimal = makeEntity.makerTokenDecimal,
			takerTokenCoinId = makeEntity.takerTokenCoinId,
			takerTokenCoinSymbol = makeEntity.takerTokenCoinSymbol,
			takerTokenCoinName = makeEntity.takerTokenCoinName,
			takerTokenCoinIconUrl = makeEntity.takerTokenCoinIconUrl,
			takerTokenContractAddress = makeEntity.takerTokenContractAddress,
			takerTokenBlockchain = makeEntity.takerTokenBlockchain,
			takerTokenDecimal = makeEntity.takerTokenDecimal
		)

		// Insert Take entities
		swap.take.forEach { take ->
			val takeEntity = take.toTakeEntity()
			queries.insertTake(
				takeId = takeEntity.takeId,
				takerId = takeEntity.takerId,
				takerRefundAddress = takeEntity.takerRefundAddress,
				takerRedeemAddress = takeEntity.takerRedeemAddress,
				makerFinalAmount = takeEntity.makerFinalAmount,
				takerFinalAmount = takeEntity.takerFinalAmount,
				makeId = takeEntity.makeId
			)
		}

		// Insert Swap entity
		val swapEntity = swap.toEntity()
		queries.insert(
			swapId = swapEntity.swapId,
			timestamp = swapEntity.timestamp,
			swapState = swapEntity.swapState,
			isRead = swapEntity.isRead,
			makeId = swapEntity.makeId,
			takeId = swapEntity.takeId,
			takerRefundAddressId = swapEntity.takerRefundAddressId,
			makerRefundAddressId = swapEntity.makerRefundAddressId,
			takerRedeemAddressId = swapEntity.takerRedeemAddressId,
			makerRedeemAddressId = swapEntity.makerRedeemAddressId,
			secret = swapEntity.secret,
			secretHash = swapEntity.secretHash,
			takerRefundTime = swapEntity.takerRefundTime,
			makerRefundTime = swapEntity.makerRefundTime,
			takerSafeTxTime = swapEntity.takerSafeTxTime,
			makerSafeTxTime = swapEntity.makerSafeTxTime,
			takerSafeTx = swapEntity.takerSafeTx,
			makerSafeTx = swapEntity.makerSafeTx,
			takerRedeemTx = swapEntity.takerRedeemTx,
			makerRedeemTx = swapEntity.makerRedeemTx,
			takerRefundTx = swapEntity.takerRefundTx,
			makerRefundTx = swapEntity.makerRefundTx,
			takerSafeAmount = swapEntity.takerSafeAmount,
			makerSafeAmount = swapEntity.makerSafeAmount
		)
	}

	suspend fun deleteSwap(id: String) = withContext(Dispatchers.IO) {
		queries.deleteById(id)
	}
}