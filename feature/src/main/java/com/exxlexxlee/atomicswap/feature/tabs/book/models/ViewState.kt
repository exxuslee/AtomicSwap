package com.exxlexxlee.atomicswap.feature.tabs.book.models

import com.exxlexxlee.atomicswap.core.swap.model.Make


data class ViewState(
    val isLoading: Boolean = false,
    val makes: List<Make> = listOf(),
)