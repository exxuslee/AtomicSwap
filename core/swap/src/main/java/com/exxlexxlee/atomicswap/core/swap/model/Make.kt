package com.exxlexxlee.atomicswap.core.swap.model

import java.math.BigDecimal

data class Make(
    val makeId: String,
    val makerId: String,
    val makerToken: Token,
    val takerToken: Token,
    val refundAddress: String,
    val redeemAddress: String,
    val amount: AmountType,
    val priceType: PriceType,
    val isOn: Boolean,
    val reservedAmount: BigDecimal,
    val refundTime: Long,
    val timestamp: Long,
)




