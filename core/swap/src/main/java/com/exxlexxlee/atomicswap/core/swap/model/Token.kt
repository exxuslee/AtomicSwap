package com.exxlexxlee.atomicswap.core.swap.model

data class Token(
    val coin: Coin,
    val contractAddress: String?,
    val blockchain: Blockchain,
    val decimal: Int,
) {
    fun badge(): String? = contractAddress?.let { blockchain.badge() }
}