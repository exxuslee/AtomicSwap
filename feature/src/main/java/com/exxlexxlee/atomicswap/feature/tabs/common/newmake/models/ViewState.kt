package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

import java.math.BigDecimal


data class ViewState(
    val swapId: String,
    val expandedTaker: Boolean = false,
    val expandedMaker: Boolean = false,
    val make: MakeViewItem = MakeViewItem(),
    val price: BigDecimal? = null,
    val balance: BigDecimal? = null,
)