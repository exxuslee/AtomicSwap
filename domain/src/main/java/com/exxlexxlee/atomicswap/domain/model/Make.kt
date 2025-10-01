package com.exxlexxlee.atomicswap.domain.model

import java.math.BigDecimal

open class Make (
    val id: String,
    val makerId: String,
    val makerToken: Token,
    val takerToken: Token,
    val makerRefundAddress: String,
    val makerRedeemAddress: String,

    val makerExactAmount: BigDecimal,
    val takerExactAmount: BigDecimal,

    val makerStartAmount: BigDecimal,
    val takerStartAmount: BigDecimal,
)
