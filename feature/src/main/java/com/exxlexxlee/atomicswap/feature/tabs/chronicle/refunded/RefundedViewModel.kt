package com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.models.ViewState

class RefundedViewModel(
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


