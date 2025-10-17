package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Coin
import java.math.BigDecimal

interface PriceRepository {
    suspend fun price(coin: Coin): BigDecimal?
    suspend fun refresh()
    suspend fun sync()
}