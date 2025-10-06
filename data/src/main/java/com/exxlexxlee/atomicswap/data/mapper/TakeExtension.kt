package com.exxlexxlee.atomicswap.data.mapper

import com.exxlexxlee.atomicswap.core.database.model.TakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.Take
import java.math.BigDecimal

internal fun TakeEntity.toDomain(make: Make): Take {
    return Take(
        takeId = takeId,
        make = make,
        takerId = takerId,
        redeemAddress = takerRedeemAddress,
        refundAddress = takerRefundAddress,
        isConfirmed = false,
        makerFinalAmount = BigDecimal(makerFinalAmount),
        takerFinalAmount = BigDecimal(takerFinalAmount),
        takerSafeAmount = BigDecimal.ZERO,
        makerSafeAmount = BigDecimal.ZERO,
    )
}

internal fun Take.toTakeEntity(): TakeEntity {
    return TakeEntity(
        takeId = takeId,
        takerId = takerId,
        takerRefundAddress = refundAddress,
        takerRedeemAddress = redeemAddress,
        makerFinalAmount = makerFinalAmount.toPlainString(),
        takerFinalAmount = takerFinalAmount.toPlainString(),
        makeId = make.makeId
    )
}