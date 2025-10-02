package com.exxlexxlee.atomicswap.feature.chronicle.main.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.Swap


data class ViewState(
    val selectedTab: FilterStateChronicle = FilterStateChronicle.ACTIVE,
    val allSwaps: List<Swap> = emptyList(),
    val filteredSwaps: List<Swap> = emptyList(),
    val isLoading: Boolean = false,
)