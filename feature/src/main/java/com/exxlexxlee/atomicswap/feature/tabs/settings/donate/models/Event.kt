package com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models

sealed class Event {
    data object AddressCopied: Event()
    data class OnAmountSelected(val amount: Int): Event()
    data class OnTokenSelected(val pos: Int): Event()
}