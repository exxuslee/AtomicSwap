package com.exxlexxlee.atomicswap.feature.common.tokens.models

import com.exxlexxlee.atomicswap.core.swap.model.FullCoin


data class ViewState(
    val isLoading: Boolean = false,
    val fullCoins: List<FullCoin> = listOf(),
)