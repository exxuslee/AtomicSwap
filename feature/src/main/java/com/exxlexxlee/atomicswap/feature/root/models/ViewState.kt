package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.navigation.Routes

data class ViewState(
    val initialRoute: String,
    val makerRoute: Routes.MakerRoute,
    val chronicleRoute: Routes.ChronicleRoute.MainRoute,
    val settingsRoute: Routes.SettingsRoute.MainRoute,
    val selectedChronicleTab: FilterStateChronicle,
    val pushUnreadCount: Int = 0,
)