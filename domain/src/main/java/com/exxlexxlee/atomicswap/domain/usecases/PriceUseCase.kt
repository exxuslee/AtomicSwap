package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.domain.repository.PriceRepository
import java.math.BigDecimal

interface PriceUseCase {
    suspend fun sync()
    suspend fun refresh()
    fun price(coin: Coin): BigDecimal?

    class Base(
        private val priceRepository: PriceRepository,
    ) : PriceUseCase {
        override suspend fun sync() {
            priceRepository.sync()
        }

        override suspend fun refresh() {
            priceRepository.refresh()
        }

        override fun price(coin: Coin): BigDecimal? {
            return priceRepository.price(coin)
        }

    }
}