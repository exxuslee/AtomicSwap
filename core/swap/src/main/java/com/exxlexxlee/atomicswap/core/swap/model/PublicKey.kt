package com.exxlexxlee.atomicswap.core.swap.model

data class PublicKey(val publicKeyHash: ByteArray, val publicKeyId: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublicKey

        if (!publicKeyHash.contentEquals(other.publicKeyHash)) return false
        if (publicKeyId != other.publicKeyId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = publicKeyHash.contentHashCode()
        result = 31 * result + publicKeyId.hashCode()
        return result
    }
}