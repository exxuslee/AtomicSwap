package com.exxlexxlee.atomicswap.feature.common.tokens.models

import com.exxlexxlee.atomicswap.core.swap.model.Token


data class ViewState(
    val isLoading: Boolean = false,
    val tokens: List<Token> = listOf(),
)