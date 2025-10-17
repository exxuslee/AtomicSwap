package com.exxlexxlee.atomicswap.core.swap.model

import java.math.BigDecimal

sealed class PriceType {
    data class Fixed(val price: BigDecimal) : PriceType()
    data class Market(val discountPercent: Int) : PriceType()
}