package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle

sealed class Event {
    data class SelectTab(val filterState: FilterStateChronicle) : Event()
}