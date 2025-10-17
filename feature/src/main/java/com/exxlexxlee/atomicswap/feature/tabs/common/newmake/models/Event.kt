package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

import com.exxlexxlee.atomicswap.core.swap.model.Token

sealed class Event {
    data object TakerTokenSheet : Event()
    data object MakerTokenSheet : Event()
    data class TakerToken(val token: Token?) : Event()
    data class MakerToken(val token: Token?) : Event()
    data object SwitchToken : Event()
    data object ClearAction : Event()
    data object SetFixedPrice : Event()
    data object SetMarketPrice : Event()
}