package com.exxlexxlee.atomicswap.feature.navigation

sealed class RoutesMain(val route: String, val pos: Int) {
    data object Maker : RoutesMain("maker", 0)
    data object Taker : RoutesMain("taker", 1)
    data object History : RoutesMain("history", 2)
    sealed class Settings(id: String) : RoutesMain("settings/$id", 3) {
        data object Main : Settings("main")
        data object Therms : Settings("terms")
        data object Language : Settings("language")
        data object Notification : Settings("notification")
        data object Donate : Settings("donate")
        data object About : Settings("about")
        data object PriceAggregator : Settings("aggregator")
    }

}
