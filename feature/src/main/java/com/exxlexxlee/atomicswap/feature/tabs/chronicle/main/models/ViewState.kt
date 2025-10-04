package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models

import com.exxlexxlee.atomicswap.core.network.ConnectionManager
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle


data class ViewState(
    val selectedTab: FilterStateChronicle = FilterStateChronicle.Active,
    val badgeType: Map<FilterStateChronicle, Int?> = mapOf(),
    val connectionState: ConnectionManager.ConnectionState = ConnectionManager.ConnectionState.Connected(
        ConnectionManager.NetworkType.WIFI
    ),

    )