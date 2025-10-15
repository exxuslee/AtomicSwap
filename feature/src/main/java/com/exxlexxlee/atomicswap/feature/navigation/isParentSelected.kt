package com.exxlexxlee.atomicswap.feature.navigation

fun Routes.isParentSelected(currentRoute: String?): Boolean {
    return when (this) {
        is Routes.BookRoute -> currentRoute?.startsWith("book/") == true
        is Routes.ChronicleRoute -> currentRoute?.startsWith("chronicle/") == true
        is Routes.SettingsRoute -> currentRoute?.startsWith("settings/") == true
    }
}