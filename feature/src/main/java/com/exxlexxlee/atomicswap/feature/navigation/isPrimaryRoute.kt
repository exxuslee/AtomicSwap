package com.exxlexxlee.atomicswap.feature.navigation

fun Routes.isPrimaryRoute(): Boolean {
    return when (this) {
        is Routes.BookRoute.MainRoute,
        is Routes.ChronicleRoute.MainRoute,
        is Routes.SettingsRoute.MainRoute -> true

        else -> false
    }
}