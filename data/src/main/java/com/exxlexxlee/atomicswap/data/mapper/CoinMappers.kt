package com.exxlexxlee.atomicswap.data.mapper

import io.horizontalsystems.marketkit.models.Coin

fun Coin.toDomain(): com.exxlexxlee.atomicswap.core.swap.model.Coin {
    return com.exxlexxlee.atomicswap.core.swap.model.Coin(
        symbol = this.code,
        name = this.name,
        iconUrl = this.image,
    )
}