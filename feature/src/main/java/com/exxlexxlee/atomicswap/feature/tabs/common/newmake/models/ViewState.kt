package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models



data class ViewState(
    val swapId: String,
    val expandedTaker: Boolean = false,
    val expandedMaker: Boolean = false,
    val make: MakeViewItem = MakeViewItem(),
)