package com.exxlexxlee.atomicswap.data.mapper

import io.horizontalsystems.marketkit.models.Token

fun Token.toDomain(): com.exxlexxlee.atomicswap.core.swap.model.Token {
    return com.exxlexxlee.atomicswap.core.swap.model.Token(
        coin = this.coin.toDomain(),
        contractAddress = this.type.values.reference.ifEmpty { null },
        blockchain = this.blockchain.toDomain(),
        decimal = this.decimals,
    )
}