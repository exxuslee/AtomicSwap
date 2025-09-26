package com.example.atomicswap.feature.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Event {
    data class Select(val type: SupportedLocales) : Event()
    data object PopBackStack: Event()
}