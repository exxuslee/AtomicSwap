package com.exxlexxlee.atomicswap.feature.navigation

import androidx.navigation.NavDestination

fun NavDestination?.asRoute(): Routes? {
    val route = this?.route ?: return null
    return when {
        // Maker
        route == Routes.BookRoute.MainRoute().route -> Routes.BookRoute.MainRoute()
        route == Routes.BookRoute.MakesRoute.route -> Routes.BookRoute.MyMakeRoute
        route == Routes.BookRoute.MyMakeRoute.route -> Routes.BookRoute.MyMakeRoute
        route == Routes.BookRoute.SubscriptionRoute.route -> Routes.BookRoute.SubscriptionRoute
        route.startsWith("book/newMake/") -> Routes.ChronicleRoute.SwapRoute

        // Chronicle
        route == Routes.ChronicleRoute.MainRoute().route -> Routes.ChronicleRoute.MainRoute()
        route == Routes.ChronicleRoute.MyMakeRoute.route -> Routes.ChronicleRoute.MyMakeRoute
        route == Routes.ChronicleRoute.ActiveRoute.route -> Routes.ChronicleRoute.ActiveRoute
        route == Routes.ChronicleRoute.CompleteRoute.route -> Routes.ChronicleRoute.CompleteRoute
        route == Routes.ChronicleRoute.RefundRoute.route -> Routes.ChronicleRoute.RefundRoute
        route.startsWith("chronicle/swap/") -> Routes.ChronicleRoute.SwapRoute

        // Settings
        route == Routes.SettingsRoute.MainRoute().route -> Routes.SettingsRoute.MainRoute()
        route == Routes.SettingsRoute.ThermsRoute.route -> Routes.SettingsRoute.ThermsRoute
        route == Routes.SettingsRoute.LanguageRoute.route -> Routes.SettingsRoute.LanguageRoute
        route == Routes.SettingsRoute.NotificationRoute.route -> Routes.SettingsRoute.NotificationRoute
        route == Routes.SettingsRoute.DonateRoute.route -> Routes.SettingsRoute.DonateRoute
        route == Routes.SettingsRoute.AboutRoute.route -> Routes.SettingsRoute.AboutRoute
        route == Routes.SettingsRoute.PriceAggregatorRoute.route -> Routes.SettingsRoute.PriceAggregatorRoute
        route == Routes.SettingsRoute.ScannerRoute.route -> Routes.SettingsRoute.ScannerRoute

        else -> null
    }
}