package com.exxlexxlee.atomicswap.core.swap.model

data class Token(
    val id: String,
    val coin: Coin,
    val contractAddress: String?,
    val blockchain: Blockchain,
    val decimal: Int,
)