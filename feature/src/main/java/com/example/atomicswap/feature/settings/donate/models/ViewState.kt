package com.example.atomicswap.feature.settings.donate.models


data class ViewState(
    val selectedAmount: Int = 25,
    val availableAmounts: List<Int> = listOf(25, 100, 1000, 10000),
)