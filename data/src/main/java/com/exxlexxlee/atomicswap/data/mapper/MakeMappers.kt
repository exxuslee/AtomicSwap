package com.exxlexxlee.atomicswap.data.mapper

import com.exxlexxlee.atomicswap.core.database.model.MakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.Make

// MakeEntity to Make domain model
fun MakeEntity.toDomain(): Make {
    return Make(
        makeId = makeId,
        makerId = makerId,
        makerToken = makerToken,
        takerToken = takerToken,
        refundAddress = refundAddress,
        redeemAddress = redeemAddress,
        adAmount = adAmount,
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
        adAmount = adAmount,
        priceType = priceType,
        isOn = isOn,
        reservedAmount = reservedAmount,
        refundTime = refundTime,
        timestamp = timestamp,
    )
}