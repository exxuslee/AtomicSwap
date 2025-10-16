package com.exxlexxlee.atomicswap.feature.tabs.common.tokens.models

import com.exxlexxlee.atomicswap.core.swap.model.Token

sealed class Action {
    data object OnDismissRequest: Action()
    data class OnSelectToken(val token: Token): Action()
}