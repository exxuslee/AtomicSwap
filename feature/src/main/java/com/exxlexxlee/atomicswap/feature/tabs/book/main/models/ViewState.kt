package com.exxlexxlee.atomicswap.feature.tabs.book.main.models

import com.exxlexxlee.atomicswap.core.network.ConnectionManager
import com.exxlexxlee.atomicswap.domain.model.FilterStateBook


data class ViewState(
    val selectedTab: FilterStateBook = FilterStateBook.Make,
    val badgeType: Map<FilterStateBook, Int?> = mapOf(),
    val connectionState: ConnectionManager.ConnectionState = ConnectionManager.ConnectionState.Connected(
        ConnectionManager.NetworkType.WIFI
    ),
)