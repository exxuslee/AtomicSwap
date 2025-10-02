package com.exxlexxlee.atomicswap.domain.model

import java.math.BigDecimal

open class Take(
    open val takeId: String,
    override val makeId: String,
    override val makerId: String,
	open val takerId: String,
    override val takerToken: Token,
    override val makerToken: Token,
    override val makerRefundAddress: String,
    override val makerRedeemAddress: String,
	open val takerRefundAddress: String,
	open val takerRedeemAddress: String,

    override val makerExactAmount: BigDecimal,
    override val takerExactAmount: BigDecimal,

    override val makerStartAmount: BigDecimal,
    override val takerStartAmount: BigDecimal,
	open val makerFinalAmount: BigDecimal,
	open val takerFinalAmount: BigDecimal,
) : Make(
	makeId,
    makerId,
	makerToken,
	takerToken,
    makerRefundAddress,
    makerRedeemAddress,
	makerExactAmount,
	takerExactAmount,
	makerStartAmount,
	takerStartAmount,
)
