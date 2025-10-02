package com.exxlexxlee.atomicswap.domain.model

import java.math.BigDecimal

open class Make (
    open val makeId: String,
    open val makerId: String,
    open val makerToken: Token,
    open val takerToken: Token,
    open val makerRefundAddress: String,
    open val makerRedeemAddress: String,

    open val makerExactAmount: BigDecimal,
    open val takerExactAmount: BigDecimal,

    open val makerStartAmount: BigDecimal,
    open val takerStartAmount: BigDecimal,
)
