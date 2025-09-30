package com.exxlexxlee.atomicswap.feature.settings.aggregator.models

import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators

data class ViewState(
    val emitters: List<SupportedAggregators> = listOf(),
    val selected: SupportedAggregators = SupportedAggregators.COIN_MARKET_CAP
)