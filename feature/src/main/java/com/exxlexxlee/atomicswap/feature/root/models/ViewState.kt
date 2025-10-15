package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateBook
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.navigation.Routes

data class ViewState(
    val initialRoute: String,
    val bookRoute: Routes.BookRoute,
    val chronicleRoute: Routes.ChronicleRoute.MainRoute,
    val settingsRoute: Routes.SettingsRoute.MainRoute,
    val selectedChronicleTab: FilterStateChronicle,
    val selectedBookTab: FilterStateBook,
    val pushUnreadCount: Int = 0,
    val swapFilterChronicleBadgeType: Map<FilterStateChronicle, Int?> = mapOf(),
    val swapFilterBookBadgeType: Map<FilterStateBook, Int?> = mapOf(),

)