package com.exxlexxlee.atomicswap.feature.chronicle.main.models

sealed class Event {
    data class OpenSwap(val swapId: String) : Event()
    data class SelectTab(val filterState: SwapFilterState) : Event()
}