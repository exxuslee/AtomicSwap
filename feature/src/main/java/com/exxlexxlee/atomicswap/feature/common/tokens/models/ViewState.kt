package com.exxlexxlee.atomicswap.feature.common.tokens.models

import com.exxlexxlee.atomicswap.core.swap.model.FullCoin


data class ViewState(
    val isLoading: Boolean = false,
    val hasMoreItems: Boolean = true,
    val title: String = "",
    val fullCoins: List<FullCoin> = listOf(),
    val isTokenView: Boolean = true,
)