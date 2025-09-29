package com.exxlexxlee.atomicswap.feature.navigation

fun RoutesMain.isParentSelected(currentRoute: String?): Boolean {
    return when (this) {
        RoutesMain.Maker -> currentRoute == RoutesMain.Maker.route
        RoutesMain.Taker -> currentRoute == RoutesMain.Taker.route
        RoutesMain.History -> currentRoute == RoutesMain.History.route
        is RoutesMain.Settings -> currentRoute?.startsWith("settings/") == true
    }
}