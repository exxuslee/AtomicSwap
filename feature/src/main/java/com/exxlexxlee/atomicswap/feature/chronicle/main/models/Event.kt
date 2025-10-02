package com.exxlexxlee.atomicswap.feature.chronicle.main.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle

sealed class Event {
    data class OpenSwap(val swapId: String) : Event()
    data class SelectTab(val filterState: FilterStateChronicle) : Event()
}