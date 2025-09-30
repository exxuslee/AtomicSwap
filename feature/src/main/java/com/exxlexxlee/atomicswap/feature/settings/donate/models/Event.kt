package com.exxlexxlee.atomicswap.feature.settings.donate.models

sealed class Event {
    data object PopBackStack: Event()
    data object AddressCopied: Event()
    data class OnAmountSelected(val amount: Int): Event()
}