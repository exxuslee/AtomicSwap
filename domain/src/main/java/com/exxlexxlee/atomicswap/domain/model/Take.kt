package com.exxlexxlee.atomicswap.domain.model

import java.math.BigDecimal

class Take(
    id: String,
    makerId: String,
    val takerId: String,
    takerToken: Token,
    makerToken: Token,
    makerRefundAddress: String,
    makerRedeemAddress: String,
	val takerRefundAddress: String,
	val takerRedeemAddress: String,

	makerExactAmount: BigDecimal,
	takerExactAmount: BigDecimal,

	makerStartAmount: BigDecimal,
	takerStartAmount: BigDecimal,
	val makerFinalAmount: BigDecimal,
	val takerFinalAmount: BigDecimal,
) : Make(
    id,
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
