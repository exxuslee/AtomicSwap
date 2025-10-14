package com.exxlexxlee.atomicswap.feature.common.tokenmodal

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.common.tokenmodal.models.Action
import com.exxlexxlee.atomicswap.feature.common.tokenmodal.models.Event
import com.exxlexxlee.atomicswap.feature.common.tokenmodal.models.ViewState


class TokensViewModel() : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}