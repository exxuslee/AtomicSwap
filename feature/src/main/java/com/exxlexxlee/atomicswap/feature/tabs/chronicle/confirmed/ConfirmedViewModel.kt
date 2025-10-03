package com.exxlexxlee.atomicswap.feature.tabs.chronicle.confirmed

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.confirmed.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.confirmed.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.confirmed.models.ViewState

class ConfirmedViewModel(
    private val swapUseCase: SwapUseCase,
): BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}


