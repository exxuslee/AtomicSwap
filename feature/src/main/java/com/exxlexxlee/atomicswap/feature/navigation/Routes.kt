package com.exxlexxlee.atomicswap.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.feature.R

sealed class Routes(
    val route: String,
    open val badge: Int? = null,
    val label: @Composable () -> String,
    val icon: @Composable () -> Painter,
    val iconSelect: @Composable () -> Painter,
) {
    sealed class BookRoute(subRoute: String) : Routes(
        "book/$subRoute",
        label = { stringResource(R.string.title_maker) },
        icon = { painterResource( R.drawable.outline_book_2_24)},
        iconSelect = { painterResource( R.drawable.book_2_24px)},
    ) {
        data class MainRoute(override val badge: Int? = null) : BookRoute("main")
        data object MakesRoute : BookRoute("book")
        data object MyMakeRoute : BookRoute("myMake")
        data object SubscriptionRoute : BookRoute("subscription")
        data object NewMakeRoute : BookRoute("newMake/{makeId}"){
            fun createRoute(makeId: String) = "book/newMake/$makeId"
        }
    }

    sealed class ChronicleRoute(subRoute: String) :
        Routes(
            "chronicle/$subRoute",
            label = { stringResource(R.string.title_chronicle) },
            icon = { painterResource( R.drawable.outline_chronic_24)},
            iconSelect = { painterResource( R.drawable.chronic_24px)},
        ) {
        data class MainRoute(override val badge: Int? = null) : ChronicleRoute("main")
        data object MyMakeRoute : ChronicleRoute("myMake")
        data object ActiveRoute : ChronicleRoute("active")
        data object CompleteRoute : ChronicleRoute("complete")
        data object RefundRoute : ChronicleRoute("refund")
        data object SwapRoute : ChronicleRoute("swap/{swapId}") {
            fun createRoute(swapId: String) = "chronicle/swap/$swapId"
        }
    }

    sealed class SettingsRoute(subRoute: String, label: @Composable () -> String) :
        Routes(
            "settings/$subRoute",
            label = label,
            icon = { painterResource( R.drawable.outline_settings_24)},
            iconSelect = { painterResource( R.drawable.baseline_settings_24)},
        ) {
        data class MainRoute(override val badge: Int? = null) :
            SettingsRoute("main", label = { stringResource(R.string.title_settings) })

        data object ThermsRoute :
            SettingsRoute("terms", label = { stringResource(R.string.terms_title) })

        data object LanguageRoute :
            SettingsRoute("language", label = { stringResource(R.string.language) })

        data object NotificationRoute :
            SettingsRoute("notification", label = { stringResource(R.string.notifications) })

        data object DonateRoute :
            SettingsRoute("donate", label = { stringResource(R.string.donate) })

        data object AboutRoute :
            SettingsRoute("about", label = { stringResource(R.string.about, R.string.app_name) })

        data object PriceAggregatorRoute :
            SettingsRoute("aggregator", label = { stringResource(R.string.price_aggregator) })

        data object ScannerRoute :
            SettingsRoute("scanner", label = { stringResource(R.string.qr_scanner) })
    }

}
