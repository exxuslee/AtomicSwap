package com.exxlexxlee.atomicswap.core.swap.model

import java.math.BigDecimal

sealed class AmountType {
    data class ExactIn(
        val makerExactAmount: BigDecimal,
        val takerStartAmount: BigDecimal,
    ) : AmountType()

    data class ExactOut(
        val makerStartAmount: BigDecimal,
        val takerExactAmount: BigDecimal,
    ) : AmountType()

}