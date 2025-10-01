package com.exxlexxlee.atomicswap.core.swap

import com.exxlexxlee.atomicswap.core.swap.model.Swap

class SwapResponse(
    val id: String,
    val initiatorRefundTime: Long,
    val responderRefundTime: Long,
    val responderRedeemPKH: ByteArray,
    val responderRefundPKH: ByteArray,
    val responderRedeemPKId: String,
    val responderRefundPKId: String,
    val responderAmount: String,
) {

    constructor(swap: Swap) : this(
        swap.id,
        swap.initiatorRefundTime,
        swap.responderRefundTime,
        swap.responderRedeemPKH,
        swap.responderRefundPKH,
        swap.responderRedeemPKId,
        swap.responderRefundPKId,
        swap.responderAmount,
    )

}
