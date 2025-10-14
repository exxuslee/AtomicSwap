package com.exxlexxlee.atomicswap.feature.common.tokens

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Action
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Event
import com.exxlexxlee.atomicswap.feature.common.tokens.models.ViewState


class TokensViewModel() : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}