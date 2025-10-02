package com.exxlexxlee.atomicswap.feature.navigation

import com.exxlexxlee.atomicswap.feature.R

sealed class RoutesMain(
    val route: String,
    open val badge: Int? = null,
    val label: Int,
    val icon: Int,
) {
    data class Maker(
        override val badge: Int? = null,
    ) : RoutesMain(
        "maker",
        label = R.string.title_maker,
        icon = R.drawable.outline_book_2_24
    )

    data class Taker(override val badge: Int? = null) :
        RoutesMain(
            "taker",
            label = R.string.title_taker,
            icon = R.drawable.outline_gavel_24
        )

    data class History(override val badge: Int? = null) :
        RoutesMain(
            "history",
            label = R.string.title_chronicle,
            icon = R.drawable.outline_chronic_24
        )

    sealed class Settings(id: String) :
        RoutesMain(
            "settings/$id",
            label = R.string.title_settings,
            icon = R.drawable.outline_settings_24
        ) {
        data class Main(override val badge: Int? = null) : Settings("main")
        data object Therms : Settings("terms")
        data object Language : Settings("language")
        data object Notification : Settings("notification")
        data object Donate : Settings("donate")
        data object About : Settings("about")
        data object PriceAggregator : Settings("aggregator")
    }

}
