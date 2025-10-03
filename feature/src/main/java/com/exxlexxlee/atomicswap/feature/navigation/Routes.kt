package com.exxlexxlee.atomicswap.feature.navigation

import com.exxlexxlee.atomicswap.feature.R
import kotlinx.serialization.Serializable

sealed class Routes(
    val route: String,
    open val badge: Int? = null,
    val label: Int,
    val icon: Int,
) {
    data class MakerRoute(
        override val badge: Int? = null,
    ) : Routes(
        "maker",
        label = R.string.title_maker,
        icon = R.drawable.outline_book_2_24
    )

    sealed class ChronicleRoute(subRoute: String) :
        Routes(
            "chronicle/$subRoute",
            label = R.string.title_chronicle,
            icon = R.drawable.outline_chronic_24
        ) {
        data class MainRoute(override val badge: Int? = null) : ChronicleRoute("main")

        data object SwapRoute : ChronicleRoute("swap/{swapId}") {
            fun createRoute(swapId: String) = "chronicle/swap/$swapId"
        }
    }

    sealed class SettingsRoute(private val subRoute: String) :
        Routes(
            "settings/$subRoute",
            label = R.string.title_settings,
            icon = R.drawable.outline_settings_24
        ) {
        data class MainRoute(override val badge: Int? = null) : SettingsRoute("main")
        data object ThermsRoute : SettingsRoute("terms")
        data object LanguageRoute : SettingsRoute("language")
        data object NotificationRoute : SettingsRoute("notification")
        data object DonateRoute : SettingsRoute("donate")
        data object AboutRoute : SettingsRoute("about")
        data object PriceAggregatorRoute : SettingsRoute("aggregator")
    }

}
