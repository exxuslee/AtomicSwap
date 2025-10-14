package com.exxlexxlee.atomicswap.core.swap.model


data class FullCoin(
    val coin: Coin,
    val tokens: List<Token>,
)