package com.exxlexxlee.atomicswap.feature.settings.donate.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Event {
    data object PopBackStack: Event()
    data class OnAmountSelected(val amount: Int): Event()
}