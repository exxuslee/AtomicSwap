package com.exxlexxlee.atomicswap.core.swap.model

data class Swap(
    val swapId: String,
    val take: Take,
    val timestamp: Long,
    val swapState: SwapState,
    val isRead: Boolean,

    val secret: String? = null,
    val secretHash: String,

    val takerSafeTxTime: Long? = null,
    val makerSafeTxTime: Long? = null,

    val takerSafeTx: String? = null,
    val makerSafeTx: String? = null,
    val takerRedeemTx: String? = null,
    val makerRedeemTx: String? = null,
    val takerRefundTx: String? = null,
    val makerRefundTx: String? = null,
    )

