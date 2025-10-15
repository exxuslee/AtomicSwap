package com.exxlexxlee.atomicswap.feature.tabs.book.my

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.ViewState

class MyMakeViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {


    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}