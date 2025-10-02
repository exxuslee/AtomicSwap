package com.exxlexxlee.atomicswap.domain.model

data class Token(
    val coin: Coin,
    val contractAddress: String?,
    val blockchain: Blockchain,
    val decimal: Int,
)