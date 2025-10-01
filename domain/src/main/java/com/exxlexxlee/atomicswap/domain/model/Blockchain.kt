package com.exxlexxlee.atomicswap.domain.model

sealed class Blockchain {
    data object Bitcoin : Blockchain()
    data object Litecoin : Blockchain()
    data object Ethereum : Blockchain()
    data object BinanceSmartChain : Blockchain()
    data object Unsupported : Blockchain()

    companion object {
        fun valueOf(blockchain: String): Blockchain {
            return when (blockchain) {
                "Bitcoin" -> Bitcoin
                "Litecoin" -> Litecoin
                "Ethereum" -> Ethereum
                "BinanceSmartChain" -> BinanceSmartChain
                else -> Unsupported
            }
        }
    }
}

fun Blockchain.toStorageName(): String {
    return when (this) {
        is Blockchain.Bitcoin -> "Bitcoin"
        is Blockchain.Litecoin -> "Litecoin"
        is Blockchain.Ethereum -> "Ethereum"
        is Blockchain.BinanceSmartChain -> "BinanceSmartChain"
        is Blockchain.Unsupported -> "Unsupported"
    }
}