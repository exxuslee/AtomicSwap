package com.exxlexxlee.atomicswap.feature.tabs.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Event {
    data class Select(val type: SupportedLocales) : Event()
}