package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Coin
import java.math.BigDecimal

interface PriceRepository {
    fun price(coin: Coin): BigDecimal?
}