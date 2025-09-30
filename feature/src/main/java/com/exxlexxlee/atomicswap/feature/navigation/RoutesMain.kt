package com.exxlexxlee.atomicswap.feature.navigation

sealed class RoutesMain(val route: String, open val badge: Int? = null) {
    data class Maker(override val badge: Int? = null) : RoutesMain("maker")
    data class Taker(override val badge: Int? = null) : RoutesMain("taker")
    data class History(override val badge: Int? = null) : RoutesMain("history")
    sealed class Settings(id: String) : RoutesMain("settings/$id") {
        data class Main(override val badge: Int? = null) : Settings("main")
        data object Therms : Settings("terms")
        data object Language : Settings("language")
        data object Notification : Settings("notification")
        data object Donate : Settings("donate")
        data object About : Settings("about")
        data object PriceAggregator : Settings("aggregator")
    }

}
