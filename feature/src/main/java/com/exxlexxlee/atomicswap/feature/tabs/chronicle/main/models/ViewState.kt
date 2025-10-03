package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle


data class ViewState(
    val selectedTab: FilterStateChronicle = FilterStateChronicle.Active,
    val badgeType: Map<FilterStateChronicle, Int?> = mapOf(),
)