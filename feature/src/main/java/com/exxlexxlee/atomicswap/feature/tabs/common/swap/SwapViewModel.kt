package com.exxlexxlee.atomicswap.feature.tabs.common.swap

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.common.swap.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.common.swap.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.swap.models.ViewState


class SwapViewModel(
    swapId: String,
    private val swapUseCase: SwapUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState(swapId)) {


    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            else -> {}
        }

    }

}