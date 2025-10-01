package com.exxlexxlee.atomicswap.domain.model

sealed class Blockchain {
    data object Bitcoin : Blockchain()
    data object Litecoin : Blockchain()
    data object Ethereum : Blockchain()
    data object BinanceSmartChain : Blockchain()
    data object Unsupported : Blockchain()
}