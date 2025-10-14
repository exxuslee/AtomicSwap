package com.exxlexxlee.atomicswap.data.mapper

import com.exxlexxlee.atomicswap.core.database.model.MakeEntity
import com.exxlexxlee.atomicswap.core.database.model.SwapEntity
import com.exxlexxlee.atomicswap.core.database.model.TakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.core.swap.model.Take

// SwapEntity to Swap domain model
fun SwapEntity.toDomain(makeEntity: MakeEntity, takeEntity: TakeEntity): Swap {
    return Swap(
        swapId = swapId,
        take = takeEntity.toDomain(makeEntity),
        timestamp = timestamp,
        swapState = swapState,
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
        makerRefundTx = makerRefundTx
    )
}




// Swap domain model to SwapEntity
fun Swap.toEntity(): SwapEntity {
    return SwapEntity(
        swapId = swapId,
        takeId = take.takeId,
        timestamp = timestamp,
        swapState = swapState,
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
        makerRefundTx = makerRefundTx
    )
}
