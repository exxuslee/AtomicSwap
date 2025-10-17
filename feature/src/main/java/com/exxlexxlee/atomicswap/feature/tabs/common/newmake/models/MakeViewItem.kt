package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.Token
import java.math.BigDecimal

data class MakeViewItem(
    val makeId: String? = null,
    val isOn: Boolean = true,
    val makerId: String? = null,
    val makerToken: Token? = null,
    val takerToken: Token? = null,
    val refundAddress: String? = null,
    val redeemAddress: String? = null,
    val adAmount: BigDecimal? = null,
    val priceType: PriceType? = null,
    val refundTime: Long = 600_000,
    val timestamp: Long = System.currentTimeMillis(),
)