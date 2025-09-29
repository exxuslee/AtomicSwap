package com.exxlexxlee.atomicswap.feature.settings.donate

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.settings.donate.models.Action
import com.exxlexxlee.atomicswap.feature.settings.donate.models.Event
import com.exxlexxlee.atomicswap.feature.settings.donate.models.ViewState

class DonateViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.PopBackStack -> viewAction = Action.PopBackStack
            is Event.OnAmountSelected -> {
                viewState = viewState.copy(selectedAmount = viewEvent.amount)
            }
        }
    }
}