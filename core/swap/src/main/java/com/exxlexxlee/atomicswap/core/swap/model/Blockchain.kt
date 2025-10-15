package com.exxlexxlee.atomicswap.core.swap.model

sealed class Blockchain(
    val iconUrl: String,
    val label: String,
) {
    data object Bitcoin : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/127/small/ordinals.png?1706606816",
        "Bitcoin",
    )

    data object Litecoin : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/51/small/bch.png?1706606492",
        "Litecoin",
    )

    data object Ethereum : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/279/small/ethereum.png?1706606803",
        "Ethereum",
    )

    data object BinanceSmartChain : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/1/small/bnb_smart_chain.png?1706606721",
        "Binance-Smart-Chain",
    )

    data object Tron : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/1094/small/TRON_LOGO.png?1706606652",
        "Tron",
    )

    data object Solana : Blockchain(
        "https://coin-images.coingecko.com/asset_platforms/images/5/small/solana.png?1706606708",
        "Solana",
    )

    data object Unsupported : Blockchain(
        "",
        "Unsupported",
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

        fun list() = listOf(Bitcoin, Ethereum, BinanceSmartChain, Solana, Tron, Litecoin)

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