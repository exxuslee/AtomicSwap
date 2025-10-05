package com.exxlexxlee.atomicswap.core.swap.model

sealed class Blockchain(open val isMain: Boolean) {
    data class Bitcoin(override val isMain: Boolean) : Blockchain(isMain)
    data class Litecoin(override val isMain: Boolean) : Blockchain(isMain)
    data class Ethereum(override val isMain: Boolean) : Blockchain(isMain)
    data class BinanceSmartChain(override val isMain: Boolean) : Blockchain(isMain)
    data class Tron(override val isMain: Boolean) : Blockchain(isMain)
    data class Solana(override val isMain: Boolean) : Blockchain(isMain)
    data class Unsupported(override val isMain: Boolean) : Blockchain(isMain)


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