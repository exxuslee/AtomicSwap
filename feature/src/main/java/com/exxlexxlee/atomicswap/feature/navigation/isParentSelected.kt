package com.exxlexxlee.atomicswap.feature.navigation

fun RoutesMain.isParentSelected(currentRoute: String?): Boolean {
    return when (this) {
        is RoutesMain.Maker -> currentRoute == RoutesMain.Maker().route
        is RoutesMain.Taker -> currentRoute == RoutesMain.Taker().route
        is RoutesMain.History -> currentRoute == RoutesMain.History().route
        is RoutesMain.Settings -> currentRoute?.startsWith("settings/") == true
    }
}