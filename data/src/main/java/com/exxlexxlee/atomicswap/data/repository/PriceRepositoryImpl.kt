package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.domain.repository.PriceRepository
import io.horizontalsystems.marketkit.MarketKit
import java.math.BigDecimal

class PriceRepositoryImpl(
    private val marketKit: MarketKit
) : PriceRepository {


    override fun price(coin: Coin): BigDecimal? {
        val coinPrice = marketKit.coinPrice(coin.uid, "USD")
        return coinPrice?.value
    }

    override suspend fun refresh() {
        marketKit.refreshCoinPrices("USD")
    }

    override suspend fun sync() {
        val coins = marketKit.topFullCoins().map { it.coin.uid }
        marketKit.coinPriceMapObservable("total", coins, "USD")
    }


}
