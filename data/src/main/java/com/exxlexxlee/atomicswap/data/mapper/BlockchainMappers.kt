package com.exxlexxlee.atomicswap.data.mapper

import io.horizontalsystems.marketkit.models.Blockchain

fun Blockchain.toDomain(): com.exxlexxlee.atomicswap.core.swap.model.Blockchain {
    return com.exxlexxlee.atomicswap.core.swap.model.Blockchain.fromUid(this.uid)
}