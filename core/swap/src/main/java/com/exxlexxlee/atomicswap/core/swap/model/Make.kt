package com.exxlexxlee.atomicswap.core.swap.model

import java.math.BigDecimal

data class Make(
    val makeId: String,
    val isOn: Boolean,
    val makerId: String,
    val makerToken: Token,
    val takerToken: Token,
    val refundAddress: String,
    val redeemAddress: String,
    val discount: Int,
    val adAmount: BigDecimal,
    val reservedAmount: BigDecimal,
    val refundTime: Long,
    val timestamp: Long,
)




