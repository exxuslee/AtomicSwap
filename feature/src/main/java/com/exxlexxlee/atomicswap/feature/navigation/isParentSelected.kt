package com.exxlexxlee.atomicswap.feature.navigation

fun Routes.isParentSelected(currentRoute: String?): Boolean {
    return when (this) {
        is Routes.MakerRoute -> currentRoute == Routes.MakerRoute().route
        is Routes.ChronicleRoute -> currentRoute?.startsWith("chronicle/") == true
        is Routes.SettingsRoute -> currentRoute?.startsWith("settings/") == true
    }
}