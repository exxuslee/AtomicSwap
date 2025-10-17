package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.domain.repository.PriceRepository
import java.math.BigDecimal

interface PriceUseCase {
    fun price(coin: Coin): BigDecimal?

    class Base(
        private val priceRepository: PriceRepository,
    ) : PriceUseCase {
        override fun price(coin: Coin): BigDecimal? {
            return priceRepository.price(coin)
        }

    }
}