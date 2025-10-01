package com.exxlexxlee.atomicswap.core.swap

import com.exxlexxlee.atomicswap.core.swap.model.Swap

data class SwapRequest(
    val id: String,
    val initiatorCoinCode: String,
    val responderCoinCode: String,
    val initiatorAmount: String,
    val responderAmount: String,
    val secretHash: ByteArray,
    val initiatorRedeemPKH: ByteArray,
    val initiatorRefundPKH: ByteArray,
    val initiatorRefundTime: Long,
    val isReactive: Boolean,
) {

    constructor(swap: Swap) : this(
        swap.id,
        swap.initiatorCoinCode,
        swap.responderCoinCode,
        swap.initiatorAmount,
        swap.responderAmount,
        swap.secretHash,
        swap.initiatorRedeemPKH,
        swap.initiatorRefundPKH,
        swap.initiatorRefundTime,
        swap.isReactive,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SwapRequest

        if (id != other.id) return false
        if (initiatorCoinCode != other.initiatorCoinCode) return false
        if (responderCoinCode != other.responderCoinCode) return false
        if (initiatorAmount != other.initiatorAmount) return false
        if (responderAmount != other.responderAmount) return false
        if (!secretHash.contentEquals(other.secretHash)) return false
        if (!initiatorRedeemPKH.contentEquals(other.initiatorRedeemPKH)) return false
        if (!initiatorRefundPKH.contentEquals(other.initiatorRefundPKH)) return false
        if (initiatorRefundTime != other.initiatorRefundTime) return false
        if (isReactive != other.isReactive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + initiatorCoinCode.hashCode()
        result = 31 * result + responderCoinCode.hashCode()
        result = 31 * result + initiatorAmount.hashCode()
        result = 31 * result + responderAmount.hashCode()
        result = 31 * result + secretHash.contentHashCode()
        result = 31 * result + initiatorRedeemPKH.contentHashCode()
        result = 31 * result + initiatorRefundPKH.contentHashCode()
        result = 31 * result + initiatorRefundTime.hashCode()
        result = 31 * result + isReactive.hashCode()
        return result
    }

}
