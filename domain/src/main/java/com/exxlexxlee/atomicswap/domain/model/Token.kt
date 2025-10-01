package com.exxlexxlee.atomicswap.domain.model

data class Token(
    override val id: String,
    override val symbol: String,
    override val name: String,
    override val iconUrl: String,
    val contractAddress: String?,
    val blockchain: Blockchain,
    val decimal: Int,
): Coin(id, symbol, name, iconUrl)