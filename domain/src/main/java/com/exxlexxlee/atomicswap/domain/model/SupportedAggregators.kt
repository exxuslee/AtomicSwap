package com.exxlexxlee.atomicswap.domain.model

import com.exxlexxlee.atomicswap.domain.R

enum class SupportedAggregators(val label: String, val icon: Int) {
    COIN_MARKET_CAP("CoinMarketCap", R.drawable.coinmarketcap_icon_32),
    BINANCE("Binance", R.drawable.binance_icon_32);

    companion object {
        fun fromLabel(label: String): SupportedAggregators {
            return entries.find { it.label == label } ?: COIN_MARKET_CAP
        }
    }
}