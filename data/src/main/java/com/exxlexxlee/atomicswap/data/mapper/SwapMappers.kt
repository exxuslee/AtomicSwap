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

// TakeEntity to Take domain model
fun TakeEntity.toDomain(makeEntity: MakeEntity): Take {
    return Take(
        takeId = takeId,
        make = makeEntity.toDomain(),
        takerId = takerId,
        redeemAddress = redeemAddress,
        refundAddress = refundAddress,
        isConfirmed = isConfirmed,
        makerFinalAmount = makerFinalAmount,
        takerFinalAmount = takerFinalAmount,
        takerSafeAmount = takerSafeAmount,
        makerSafeAmount = makerSafeAmount
    )
}

// MakeEntity to Make domain model
fun MakeEntity.toDomain(): Make {
    return Make(
        makeId = makeId,
        makerId = makerId,
        makerToken = makerToken,
        takerToken = takerToken,
        refundAddress = refundAddress,
        redeemAddress = redeemAddress,
        amount = amount,
        priceType = priceType,
        isOn = isOn,
        reservedAmount = reservedAmount,
        refundTime = refundTime,
        timestamp = timestamp
    )
}

// Make domain model to MakeEntity
fun Make.toMakeEntity(): MakeEntity {
    return MakeEntity(
        makeId = makeId,
        makerId = makerId,
        makerToken = makerToken,
        takerToken = takerToken,
        refundAddress = refundAddress,
        redeemAddress = redeemAddress,
        amount = amount,
        priceType = priceType,
        isOn = isOn,
        reservedAmount = reservedAmount,
        refundTime = refundTime,
        timestamp = timestamp
    )
}

// Take domain model to TakeEntity
fun Take.toTakeEntity(): TakeEntity {
    return TakeEntity(
        takeId = takeId,
        makeId = make.makeId,
        takerId = takerId,
        redeemAddress = redeemAddress,
        refundAddress = refundAddress,
        isConfirmed = isConfirmed,
        makerFinalAmount = makerFinalAmount,
        takerFinalAmount = takerFinalAmount,
        takerSafeAmount = takerSafeAmount,
        makerSafeAmount = makerSafeAmount
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
