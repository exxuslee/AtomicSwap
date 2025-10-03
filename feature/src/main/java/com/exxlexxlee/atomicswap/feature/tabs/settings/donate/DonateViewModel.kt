package com.exxlexxlee.atomicswap.feature.tabs.settings.donate

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.ViewState

class DonateViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.OnAmountSelected -> viewState = viewState.copy(selectedAmount = viewEvent.amount)
            is Event.OnTokenSelected -> viewState = viewState.copy(selectedToken = viewEvent.pos)
            Event.AddressCopied -> viewState = viewState.copy(isAddressCopied = true)
        }
    }
}