package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.domain.model.FilterStateBook
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle

sealed class Event {
    data class MainRoute(val route: String) : Event()
    data class ChronicleTab(val filterState: FilterStateChronicle) : Event()
    data class BookTab(val filterState: FilterStateBook) : Event()


}