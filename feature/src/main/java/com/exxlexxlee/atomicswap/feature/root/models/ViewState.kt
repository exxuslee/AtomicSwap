package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.feature.navigation.RoutesMain

data class ViewState(
    val initialRoute: String,
    val taker: RoutesMain,
    val maker: RoutesMain,
    val history: RoutesMain,
    val settings: RoutesMain,
    val bottomDestinations: List<RoutesMain> = listOf(taker, maker, history, settings),
)