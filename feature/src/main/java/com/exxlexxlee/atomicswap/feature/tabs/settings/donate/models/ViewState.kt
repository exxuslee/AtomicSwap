package com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models

import com.exxlexxlee.atomicswap.feature.R


data class ViewState(
    val donates: List<DonateViewItem>,
    val availableAmounts: List<Pair<Int, Int>> = listOf(
        R.drawable.outline_paid_24 to 25,
        R.drawable.outline_money_24 to 100,
        R.drawable.outline_account_balance_wallet_24 to 1000,
        R.drawable.outline_money_bag_24 to 10000,
    ),
    val availableTicker: List<Pair<Int, Int>> = listOf(
        R.drawable.outline_paid_24 to 25,
        R.drawable.outline_money_24 to 100,
        R.drawable.outline_account_balance_wallet_24 to 1000,
    ),
    val selectedAmount: Int = availableAmounts[1].second,
    val selectedTicker: Int = 1,
    val selectedChain: Int = 0,
    val isAddressCopied: Boolean = false,
)