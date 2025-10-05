package com.exxlexxlee.atomicswap.core.swap.model

import java.math.BigDecimal


data class Take(
    val takeId: String,
    val make: Make,
    val takerId: String,
    val redeemAddress: String,
    val refundAddress: String,
    val isConfirmed: Boolean,

    val makerFinalAmount: BigDecimal,
    val takerFinalAmount: BigDecimal,
    val takerSafeAmount: BigDecimal,
    val makerSafeAmount: BigDecimal,

    )

