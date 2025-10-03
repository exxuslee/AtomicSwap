package com.exxlexxlee.atomicswap.domain.repository

import java.math.BigDecimal

interface PriceRepository {
    fun price(token: String): BigDecimal
}