package com.exxlexxlee.atomicswap.domain.model

import com.exxlexxlee.atomicswap.domain.model.serrialize.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

data class Swap(
    val take: List<Take>,
    val takeId: String? = null,
    val make: Make,
    val swapId: String,
    val timestamp: Long,
    val swapState: SwapState,
    val takerRefundAddressId: String? = null,
    val makerRefundAddressId: String? = null,
    val takerRedeemAddressId: String? = null,
    val makerRedeemAddressId: String? = null,
    val isRead: Boolean,

    val secret: String? = null,
    val secretHash: String,

    val takerRefundTime: Int,
    val makerRefundTime: Int,
    val takerSafeTxTime: Long? = null,
    val makerSafeTxTime: Long? = null,

    val takerSafeTx: String? = null,
    val makerSafeTx: String? = null,
    val takerRedeemTx: String? = null,
    val makerRedeemTx: String? = null,
    val takerRefundTx: String? = null,
    val makerRefundTx: String? = null,

val takerSafeAmount: BigDecimal,
val makerSafeAmount: BigDecimal,

    )

