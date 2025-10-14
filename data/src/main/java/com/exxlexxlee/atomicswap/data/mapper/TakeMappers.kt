package com.exxlexxlee.atomicswap.data.mapper

import com.exxlexxlee.atomicswap.core.database.model.MakeEntity
import com.exxlexxlee.atomicswap.core.database.model.TakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.Take

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