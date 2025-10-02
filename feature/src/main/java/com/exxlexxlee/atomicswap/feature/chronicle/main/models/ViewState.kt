package com.exxlexxlee.atomicswap.feature.chronicle.main.models

import com.exxlexxlee.atomicswap.domain.model.Swap

enum class SwapFilterState {
    ALL,
    ACTIVE,
    REDEEM,
    REFUND
}

data class ViewState(
    val selectedTab: SwapFilterState = SwapFilterState.ALL,
    val allSwaps: List<Swap> = emptyList(),
    val filteredSwaps: List<Swap> = emptyList(),
    val isLoading: Boolean = false,
)