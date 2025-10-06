package com.exxlexxlee.atomicswap.data.mapper

import com.exxlexxlee.atomicswap.core.database.MakeEntity
import com.exxlexxlee.atomicswap.core.database.SwapEntity
import com.exxlexxlee.atomicswap.core.database.TakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.core.swap.model.SwapState

internal fun SwapEntity.toDomain(
    makeEntity: MakeEntity,
    takeEntity: TakeEntity
): Swap {
    val make = makeEntity.toDomain()
    val take = takeEntity.toDomain(make)

    return Swap(
        swapId = swapId,
        take = take,
        timestamp = timestamp,
        swapState = SwapState.fromValue(swapState.toInt()),
        isRead = isRead,
        secret = secret,
        secretHash = secretHash,
        takerSafeTxTime = takerSafeTxTime,
        makerSafeTxTime = makerSafeTxTime,
        takerSafeTx = takerSafeTx,
        makerSafeTx = makerSafeTx,
        takerRedeemTx = takerRedeemTx,
        makerRedeemTx = makerRedeemTx,
        takerRefundTx = takerRefundTx,
        makerRefundTx = makerRefundTx,
    )
}

internal fun Swap.toEntity(): SwapEntity {
    return SwapEntity(
        swapId = swapId,
        timestamp = timestamp,
        swapState = swapState.step,
        isRead = isRead,
        makeId = take.make.makeId,
        takeId = take.takeId,
        takerRefundAddressId = null,
        makerRefundAddressId = null,
        takerRedeemAddressId = null,
        makerRedeemAddressId = null,
        secret = secret,
        secretHash = secretHash,
        takerRefundTime = 0,
        makerRefundTime = 0,
        takerSafeTxTime = takerSafeTxTime,
        makerSafeTxTime = makerSafeTxTime,
        takerSafeTx = takerSafeTx,
        makerSafeTx = makerSafeTx,
        takerRedeemTx = takerRedeemTx,
        makerRedeemTx = makerRedeemTx,
        takerRefundTx = takerRefundTx,
        makerRefundTx = makerRefundTx,
        takerSafeAmount = "0",
        makerSafeAmount = "0",
    )
}



