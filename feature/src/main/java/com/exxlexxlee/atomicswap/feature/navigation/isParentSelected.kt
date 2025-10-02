package com.exxlexxlee.atomicswap.feature.navigation

fun Routes.isParentSelected(currentRoute: String?): Boolean {
    return when (this) {
        is Routes.Maker -> currentRoute == Routes.Maker().route
        is Routes.Chronicle -> currentRoute?.startsWith("chronicle/") == true
        is Routes.Settings -> currentRoute?.startsWith("settings/") == true
    }
}