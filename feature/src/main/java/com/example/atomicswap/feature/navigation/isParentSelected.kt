package com.example.atomicswap.feature.navigation

fun Routes.isParentSelected(currentRoute: String?): Boolean {
    return when (this) {
        Routes.Maker -> currentRoute == Routes.Maker.route
        Routes.Taker -> currentRoute == Routes.Taker.route
        Routes.History -> currentRoute == Routes.History.route
        is Routes.Settings -> currentRoute?.startsWith("settings/") == true
    }
}