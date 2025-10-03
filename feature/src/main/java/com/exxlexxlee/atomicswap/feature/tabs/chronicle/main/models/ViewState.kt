package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.Swap


data class ViewState(
    val selectedTab: FilterStateChronicle = FilterStateChronicle.Active,
    val badeType: Map<FilterStateChronicle, Int?> = mapOf(),
)