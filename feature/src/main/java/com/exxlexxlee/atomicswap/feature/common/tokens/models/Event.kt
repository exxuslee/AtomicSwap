package com.exxlexxlee.atomicswap.feature.common.tokens.models

import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Token


sealed class Event {
    data class Title(val title: String) : Event()
    data object OnDismissRequest : Event()
    data object OnLoadMore : Event()
    data class OnSelectToken(val token: Token) : Event()
    data class Filter(val text: String) : Event()
    data object OnTokenView : Event()
    data class ChainCheck(val chain: Blockchain) : Event()
}