package com.example.atomicswap.navigation

sealed class Routes(val route: String, val pos: Int) {
    data object Taker : Routes("taker",0)
    data object Maker : Routes("maker", 1)
    data object History : Routes("history", 2)
    data object Settings : Routes("settings", 3)
}