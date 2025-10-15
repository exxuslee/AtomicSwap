package com.exxlexxlee.atomicswap.feature.tabs.book.make.models

import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.Token


data class ViewState(
    val isLoading: Boolean = true,
    val makes: List<Make> = listOf(),
    val filterToken: Pair<Token?, Token?> = null to null,
    val expandedTaker: Boolean = false,
    val expandedMaker: Boolean = false,
    )


