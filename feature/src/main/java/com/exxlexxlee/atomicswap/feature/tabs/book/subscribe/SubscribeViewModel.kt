package com.exxlexxlee.atomicswap.feature.tabs.book.subscribe

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.tabs.book.subscribe.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.subscribe.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.subscribe.models.ViewState

class SubscribeViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {


    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}