package com.exxlexxlee.atomicswap.core.swap.model

sealed class Blockchain(
    val iconUrl: String,
) {
    data object Bitcoin : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/127/small/ordinals.png?1706606816"
    )

    data object Litecoin : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/51/small/bch.png?1706606492"
    )

    data object Ethereum : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/279/small/ethereum.png?1706606803"
    )

    data object BinanceSmartChain : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/1/small/bnb_smart_chain.png?1706606721"
    )

    data object Tron : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/1094/small/TRON_LOGO.png?1706606652"
    )

    data object Solana : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/5/small/solana.png?1706606708"
    )

    data object Unsupported : Blockchain(
        ""
    )


    companion object {
        fun fromUid(uid: String): Blockchain =
            when (uid) {
                "bitcoin" -> Bitcoin
                "litecoin" -> Litecoin
                "ethereum" -> Ethereum
                "binance-smart-chain" -> BinanceSmartChain
                "solana" -> Solana
                "tron" -> Tron
                else -> Unsupported
            }
    }

    fun badge(): String? {
        return when (this) {
            is Bitcoin -> null
            is Litecoin -> null
            is Ethereum -> "ERC-20"
            is BinanceSmartChain -> "BEP-20"
            is Unsupported -> null
            is Solana -> "SOL"
            is Tron -> "TRC-20"
        }
    }

    fun isSupported() = this != Unsupported
}