package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.feature.navigation.RoutesMain

data class ViewState(
    val initialRoute: String,
    val taker: RoutesMain.Taker,
    val maker: RoutesMain.Maker,
    val history: RoutesMain.History,
    val settings: RoutesMain.Settings,
)