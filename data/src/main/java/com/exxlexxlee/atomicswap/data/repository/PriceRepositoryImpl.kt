package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.domain.repository.PriceRepository
import com.google.gson.Gson
import io.horizontalsystems.marketkit.MarketKit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.rx2.asFlow
import timber.log.Timber
import java.math.BigDecimal

class PriceRepositoryImpl(
    private val marketKit: MarketKit
) : PriceRepository {

    val pricesFlow = marketKit.coinPriceMapObservable(
        "total", marketKit.topFullCoins().map { it.coin.uid }, "USD"
    ).asFlow().flowOn(Dispatchers.IO).map { pair -> pair.mapValues { it.value.value } }

    override suspend fun price(coin: Coin): BigDecimal? {
        marketKit.coinPriceObservable("total", coin.uid, "USD")
        val coinPrice = marketKit.coinPrice(coin.uid, "USD")
        return coinPrice?.value
    }

    override suspend fun refresh() {
        marketKit.refreshCoinPrices("USD")
    }

    override suspend fun sync() {
        val coins = marketKit.topFullCoins().map { it.coin.uid }
        Timber.d(Gson().toJson(coins))
    }

}
