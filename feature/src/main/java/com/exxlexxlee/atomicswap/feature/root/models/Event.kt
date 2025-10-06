package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle

sealed class Event {
    data class SelectMainRoute(val route: String) : Event()
    data class SelectChronicleTab(val filterState: FilterStateChronicle) : Event()
}