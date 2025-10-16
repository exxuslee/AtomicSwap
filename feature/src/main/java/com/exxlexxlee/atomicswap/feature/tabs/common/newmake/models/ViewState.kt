package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

import com.exxlexxlee.atomicswap.core.swap.model.Token


data class ViewState(
    val swapId: String,
    val tokenPair: Pair<Token?, Token?> = null to null,
    val expandedTaker: Boolean = false,
    val expandedMaker: Boolean = false,
)