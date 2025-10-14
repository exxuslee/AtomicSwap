package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle

sealed class Event {
    data class MainRoute(val route: String) : Event()
    data class ChronicleTab(val filterState: FilterStateChronicle) : Event()
    data object TakerTokenSheet : Event()
    data object MakerTokenSheet : Event()
    data class TakerToken(val token: Token?) : Event()
    data class MakerToken(val token: Token?) : Event()
    data object SwitchToken : Event()

    data object ClearAction : Event()
}