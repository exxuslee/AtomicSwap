package com.example.atomicswap.feature.navigation

sealed class Routes(val route: String, val pos: Int) {
    data object Maker : Routes("maker", 0)
    data object Taker : Routes("taker",1)
    data object History : Routes("history", 2)
    data object Settings : Routes("settings", 3)
}