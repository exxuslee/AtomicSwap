package com.exxlexxlee.atomicswap.feature.navigation

sealed class Routes(val route: String, val pos: Int) {
    data object Maker : Routes("maker", 0)
    data object Taker : Routes("taker", 1)
    data object History : Routes("history", 2)
    sealed class Settings(id: String) : Routes("settings/$id", 3) {
        data object Main : Settings("main")
        data object Therms : Settings("terms")
        data object Language : Settings("language")
        data object Notification : Settings("notification")
        data object Donate : Settings("donate")
        data object About : Settings("about")
    }

}
