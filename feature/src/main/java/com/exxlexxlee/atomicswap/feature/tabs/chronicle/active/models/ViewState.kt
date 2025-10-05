package com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.models

import com.exxlexxlee.atomicswap.core.swap.model.Swap


data class ViewState(
    val isLoading: Boolean = true,
    val swaps: List<Swap> = listOf(),
)