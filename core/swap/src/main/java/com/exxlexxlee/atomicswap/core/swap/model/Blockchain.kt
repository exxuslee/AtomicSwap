package com.exxlexxlee.atomicswap.core.swap.model

sealed class Blockchain(
    open val isMain: Boolean,
    val iconUrl: String
) {
    data class Bitcoin(
        override val isMain: Boolean,
    ) : Blockchain(isMain, "https://coin-images.coingecko.com/asset_platforms/images/127/small/ordinals.png?1706606816")

    data class Litecoin(
        override val isMain: Boolean,
    ) : Blockchain(isMain, "https://coin-images.coingecko.com/asset_platforms/images/51/small/bch.png?1706606492")

    data class Ethereum(
        override val isMain: Boolean,
    ) : Blockchain(isMain, "https://coin-images.coingecko.com/asset_platforms/images/279/small/ethereum.png?1706606803")

    data class BinanceSmartChain(
        override val isMain: Boolean,
    ) : Blockchain(isMain, "https://coin-images.coingecko.com/asset_platforms/images/1/small/bnb_smart_chain.png?1706606721")

    data class Tron(
        override val isMain: Boolean,
    ) : Blockchain(isMain, "https://coin-images.coingecko.com/asset_platforms/images/1094/small/TRON_LOGO.png?1706606652")

    data class Solana(
        override val isMain: Boolean,
    ) : Blockchain(isMain, "icon")

    data class Unsupported(
        override val isMain: Boolean,
    ) : Blockchain(isMain, "https://coin-images.coingecko.com/asset_platforms/images/5/small/solana.png?1706606708")


    companion object {
        fun valueOf(storageName: String): Blockchain {
            val (name, isMainStr) = storageName.split("|")
            return when (name) {
                "Bitcoin" -> Bitcoin(isMainStr.toBoolean())
                "Litecoin" -> Litecoin(isMainStr.toBoolean())
                "Ethereum" -> Ethereum(isMainStr.toBoolean())
                "BinanceSmartChain" -> BinanceSmartChain(isMainStr.toBoolean())
                "Tron" -> Tron(isMainStr.toBoolean())
                "Solana" -> Solana(isMainStr.toBoolean())
                else -> Unsupported(isMainStr.toBoolean())
            }
        }
    }
}

fun Blockchain.toStorageName(): String {
    return when (this) {
        is Blockchain.Bitcoin -> "Bitcoin|${this.isMain}"
        is Blockchain.Litecoin -> "Litecoin|${this.isMain}"
        is Blockchain.Ethereum -> "Ethereum|${this.isMain}"
        is Blockchain.BinanceSmartChain -> "BinanceSmartChain|${this.isMain}"
        is Blockchain.Unsupported -> "Unsupported|${this.isMain}"
        is Blockchain.Solana -> "Solana|${this.isMain}"
        is Blockchain.Tron -> "Tron|${this.isMain}"
    }
}