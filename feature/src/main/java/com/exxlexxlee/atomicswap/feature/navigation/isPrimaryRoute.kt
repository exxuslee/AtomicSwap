package com.exxlexxlee.atomicswap.feature.navigation

fun Routes.isPrimaryRoute(): Boolean {
    return when (this) {
        is Routes.MakerRoute -> true
        is Routes.ChronicleRoute.MainRoute -> true
        is Routes.SettingsRoute.MainRoute -> true
        else -> false
    }
}