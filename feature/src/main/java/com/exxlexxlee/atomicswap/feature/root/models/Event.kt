package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle

sealed class Event {
    data class MainRoute(val route: String) : Event()
    data class ChronicleTab(val filterState: FilterStateChronicle) : Event()
    data object TakerToken : Event()
    data object MakerToken : Event()
}